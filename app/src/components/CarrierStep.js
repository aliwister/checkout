import React, {useState} from 'react';
import {Container, Grid, Paper, Typography, TextField } from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";
import {useMutation, useQuery} from "@apollo/react-hooks";
import {CARRIERS} from "../graph/CARRIERS";
import {SET_CARRIER} from "../graph/SET_CARRIER";

const InfoDiv = styled.div`
  //padding: ${ props  =>  props.theme.spacing(8)}px;
  margin-bottom: ${ props  =>  props.theme.spacing(4)}px;
`;

export const CarrierStep = ({state, dispatch}) => {
    const { register, handleSubmit } = useForm();
    const { data, error, loading } = useQuery(CARRIERS, {
        variables: {state: 'muscat', city:'mutrah', weight:2}
    });
    const [setCarrierMutation,{loading2, data2}] = useMutation(SET_CARRIER);
    const [carrier, setCarrier] = useState();

    if (loading) {
        return <div>loading...</div>;
    }
    else
        console.log(data);

    const onSubmit = async () => {
        const {
            data: { setCarrier },
        } = await setCarrierMutation({
            variables: {value: carrier, secureKey}
        });
        console.log(setCarrier, 'cart_info');
        if(setCarrier) {
            dispatch({type: 'SET_CARRIER', payload: data.carriers.filter(x => x.value === carrier)[0]});
        }
    }
    return (
        <React.Fragment>
            <Typography variant="h6">Carrier Step</Typography>
            <InfoDiv>
                <form onSubmit={handleSubmit(onSubmit)}>
                    {data.carriers.map(x => (
                        <Grid item sm={6} key={x.value}>
                            <input name="Carrier" type="radio" value={x.value} key={x.value}
                                   ref={register({ required: true })}
                                   onChange={() => setCarrier(x.value)}
                                   checked={carrier === x.value}
                            />
                            <span>{x.name} - OMR {x.cost} </span>
                        </Grid>
                    ))}
                    <input type="submit" />
                </form>

            </InfoDiv>
        </React.Fragment>
    )
}