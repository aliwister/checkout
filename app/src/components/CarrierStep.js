import React, { useState } from 'react';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";
import { useMutation, useQuery } from "@apollo/react-hooks";
import { CARRIERS } from "../graph/CARRIERS";
import { SET_CARRIER } from "../graph/SET_CARRIER";
import { ButtonDiv, NavButton } from "../App.styles";
import Loader from "./Loader";

import { Heading, Container } from 'react-bulma-components';
import 'react-bulma-components/dist/react-bulma-components.min.css';
import { Form } from 'react-bulma-components';
const { Radio } = Form;


const InfoDiv = styled.div`
  //padding: ${props => props.theme.spacing(8)}px;
  margin-bottom: ${props => props.theme.spacing(4)}px;
`;

const RadioDiv = styled.div`
  width: 100%;
`;

const CerrierContainer = styled(Container)`
  border: 1px solid #dbdbdb;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: -1px;
  padding: 10px;
  &:first-child {
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
  }
  &:nth-last-child(3) {
    border-bottom-left-radius: 5px;
    border-bottom-right-radius: 5px;
  } 
`;

const CerrierRadio = styled(Radio)`
  display: flex;
  line-height: 1;
`;

const NameSpan = styled.span`
  margin-left: 10px;
  flex: 1;
`;

export const CarrierStep = ({ state, dispatch }) => {
  const { register, handleSubmit } = useForm();
  const { data, error, loading } = useQuery(CARRIERS, {
    variables: { state: 'muscat', city: 'mutrah', weight: 2 }
  });
  const [setCarrierMutation, { loading2, data2 }] = useMutation(SET_CARRIER);
  const [carrier, setCarrier] = useState();

  if (loading) {
    return <div>loading...</div>;
  }
  else
    console.log(data);

  const clickCarrier = async (carrier) => {
    await setCarrier(carrier.value);
    dispatch({ type: 'SET_CARRIER', payload: carrier });
  }

  const onSubmit = async () => {
    const {
      data: { setCarrier },
    } = await setCarrierMutation({
      variables: { value: carrier, secureKey }
    });
    console.log(setCarrier, 'cart_info');
    if (setCarrier) {
      dispatch({ type: 'NEXT' });
    }

  }

  return (
    <React.Fragment>
      <Heading size={6}>Carrier Step</Heading>
      <InfoDiv>
        <form onSubmit={handleSubmit(onSubmit)}>
          {data.carriers.map(x => (
            <CerrierContainer item sm={12} key={x.value}>
              <RadioDiv onClick={() => clickCarrier(x)}>
                  <CerrierRadio
                    name="carrier"
                    onChange={(e) => clickCarrier(x)}
                    checked={carrier === x.value}
                    value={x.value}
                    key={x.value}
                   >
                   <NameSpan>{x.name}</NameSpan>
                   <span>{x.cost} OMR</span>
                   </CerrierRadio>
              </RadioDiv>
            </CerrierContainer>
          ))}
          <NavButton type="submit" variant="contained"
            style={{ margin: "0px", marginTop: "20px" }}>
            {(loading) ? <Loader /> : (
              <span>Next</span>
            )}
          </NavButton>
          <NavButton variant="contained" style={{ marginTop: "20px" }}
            onClick={() => dispatch({ type: 'PREV' })}>
            {(loading) ? <Loader /> : (
              <span>Back</span>
            )}
          </NavButton>
        </form>

      </InfoDiv>
    </React.Fragment>
  )
}
