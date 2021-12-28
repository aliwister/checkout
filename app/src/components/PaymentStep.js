import React, { useState } from 'react';
import styled from 'styled-components';
import { useForm } from "react-hook-form";
import CkoFrames from './CkoFrames';
import { useMutation, useQuery } from "@apollo/react-hooks";
import { PROCESS_PAYMENT } from "../graph/PROCESS_PAYMENT";
import { PAYMENT_METHODS } from "../graph/PAYMENT_METHODS";
import { NavButton } from "../App.styles";
import Loader from "./Loader/Loader";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import makeStyles from "@material-ui/core/styles/makeStyles";

import Heading from 'react-bulma-components/lib/components/heading';
import Container from 'react-bulma-components/lib/components/container';
import Card from 'react-bulma-components/lib/components/card';
import Image from 'react-bulma-components/lib/components/image';
import Content from 'react-bulma-components/lib/components/content';
import Radio from 'react-bulma-components/lib/components/form/components/radio';

const InfoDiv = styled.div`
  //padding: ${props => props.theme.spacing(8)}px;
  margin-bottom: ${props => props.theme.spacing(4)}px;
`;

const PaymentSelectRadio = styled(Radio)`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const CheckoutButtonContainer = styled(Container)`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: row;
  justify-content: flex-end;
  padding: 15px 0px;
`;

const PaymentItemCard = styled(Card)`
  border: 1px solid #999999;
  border-radius: 5px 5px 0 0;
  :nth-child(2) {
    border-top: none;
    border-radius: 0 0 5px 5px;
  }
`;

const initialState = {
  token: false,
  hideFrame: false
};

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: '#fff',
  },
}));

function reducer(state, action) {
  switch (action.type) {
    case 'PROCESS_BEGIN': ;
      console.log('PROCESS_BEGIN', action.payload);
      return { token: action.payload };
    case 'PROCESS_DONE':
      console.log('done');
      return {
        token: false,
        hideFrame: true
      };
    default:
      throw new Error('Unknown step');
  }

}

export const PaymentStep = ({ state, dispatch }) => {

  const classes = useStyles();
  const { register, handleSubmit } = useForm();
  //const [state, dispatch] = useReducer(reducer, initialState);
  const { data, error, loading } = useQuery(PAYMENT_METHODS, {
    variables: { currency: state.cart.currency }
  });
  const [processPaymentMutation, { loading2, data2 }] = useMutation(PROCESS_PAYMENT);
  const [paymentMethod, setPaymentMethod] = useState();
  const [name, setName] = useState(state.cart.name);
  const [open, setOpen] = React.useState(false);
  const [once, setOnce] = React.useState(false);

  const codeStr = `
<!-- Event snippet for Purchase conversion page -->
<script>
  gtag('event', 'conversion', {
      'send_to': 'AW-930697440/hIuGCIaHucICEOCh5bsD',
      'transaction_id': ''
  });
</script>
`


  const onSubmit = async () => {
    //console.log("In onsubmit");
    //console.log(data);
    setOpen(true);
    const {
      data: { processPayment },
    } = await processPaymentMutation({
      variables: { token: null, ref: paymentMethod, secureKey }
    });
    //console.log(processPayment);
    if (processPayment.status === 'REDIRECT')
      window.location = processPayment.payload;
    else
      alert("Payment unsuccessful");
  }

  const handleProcessPayment = async (token) => {
    //console.log('In handleProcessPayment');
    setOnce(true);
    if (once)
      return;
    const {
      data: { processPayment },
    } = await processPaymentMutation({
      variables: { token, ref: paymentMethod, secureKey }
    });
    if (processPayment.status == 'REDIRECT')
      window.location = processPayment.payload;
    else if (processPayment.status == 'DECLINED') {
      alert(processPayment.message);
      window.location.reload();
    }


    //   dispatch({type: 'PROCESS_DONE'});
    //sendTokenToServer(formData.email);
    return false;
  }
  if (loading)
    return "loading";

  return (
    <React.Fragment>
      <Backdrop className={classes.backdrop} open={open} >
        <CircularProgress color="inherit" />
      </Backdrop>
      <Heading size={5}>Payment Step</Heading>
      <Container>
        {data.paymentMethods.map((x, i) => (
          <PaymentItemCard key={i} onClick={() => setPaymentMethod(x.ref)}>
            <Card.Content>
              <PaymentSelectRadio
                checked={paymentMethod === x.ref}
                onChange={() => setPaymentMethod(x.ref)}
                value={x.ref}
                key={x.ref}
                name={x.label}
              >
                &nbsp;&nbsp;
                {x.image && (<Image src={require('../assets/' + x.image)} />)}
               <span> &nbsp; &nbsp; {x.label} </span>
              </PaymentSelectRadio>
              {(x.ref == 'checkoutcom' && paymentMethod === x.ref) && (

                <CkoFrames handleProcessPayment={handleProcessPayment} customerName={name} />

              )}
              {(x.ref == 'bankwire' && paymentMethod === x.ref) && (

                  <Content>
                    <br/>
                    Please transfer your payment to our Bank Muscat Account
                    <br/>
                    <code>0333015845260014 (Name: BADAL TRAD)</code>
                    <br/>
                    Then confirm by sending us your Bank receipt with your order number to <a href="mailto:help@badals.com">help@badals.com</a>

                  </Content>

              )}
            </Card.Content>
          </PaymentItemCard>
        ))}
      </Container>
      
      <CheckoutButtonContainer>
        <form onSubmit={handleSubmit(onSubmit)}>
          {(paymentMethod != 'checkoutcom') && (<><NavButton type="submit" variant="contained"
            >
            Complete Order
                </NavButton>
            <NavButton variant="contained"
              onClick={() => dispatch({ type: 'PREV' })}>
              {(loading) ? <Loader /> : (
                <span>Back</span>
              )}
            </NavButton>
          </>
          )}
        </form>
      </CheckoutButtonContainer>
      <div dangerouslySetInnerHTML={{ __html: codeStr }} />
    </React.Fragment>
  )
}