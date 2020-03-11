import React, {useEffect, useReducer, useState} from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import PaymentForm from "./components/old/PaymentForm";
import Review from "./components/old/Review";
import Button from "@material-ui/core/Button";
import Step from "@material-ui/core/Step";
import Stepper from "@material-ui/core/Stepper";
import StepLabel from "@material-ui/core/StepLabel";
import Card from "@material-ui/core/Card";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import AppBar from "@material-ui/core/AppBar";
import Grid from "@material-ui/core/Grid";

import { InfoStep } from './components/InfoStep';
import { PaymentStep } from './components/PaymentStep';
import { CarrierStep } from './components/CarrierStep';
import { CartSummary } from './components/Cart/CartSummary';


import {useMutation, useQuery} from '@apollo/react-hooks';
import { withApollo } from "react-apollo";

import {
    RootGrid,
    InfoContainer,
    RightGrid,
    LeftGrid,
    Wrapper,
    Title,
    NavButton,
    ButtonDiv,
    CheckoutPaper, ContainerGrid
} from './App.styles'
import {CART} from './graph/cart';



const steps = ['Shipping address', 'Payment details', 'Review your order'];
function reducer(state, action) {
    switch (action.type) {
        case 'NEXT':;
            return {step: state.step + 1}
        case 'PREV':
            return {step: state.step - 1};
        case 'SET_CARRIER':
            return {
                ...state,
                step: state.step+1,
                carrier : action.payload
            }
        default:
            throw new Error('Unknown step');
    }

}

function getStepContent(step, dataAsState, dispatch, setCarrier) {
    console.log(step);
    switch (step) {
        case 0:
            return <InfoStep state={dataAsState} dispatch={dispatch}/>;
        case 1:
            return <CarrierStep state={dataAsState} dispatch={dispatch} />;
        case 2:
            return <PaymentStep state={dataAsState} dispatch={dispatch}/>;
        default:
            throw new Error('Unknown step');
    }
}

const App = (props) => {
    const initialState = {
        step: 0
    }
    const steps = ['Customer Information', 'Select Carrier', 'Select Payment']

    const [state, dispatch] = useReducer(reducer, initialState);
    const [spacing, setSpacing] = React.useState(5);
    const [activeStep, setActiveStep] = React.useState(0);
    const [carrier, setCarrier] = useState();
    const { client } = props;

    /*useEffect(() => {
        client.resetStore()
    }, []);
*/

    const handleNext = () => {
        setActiveStep(activeStep + 1);
    };

    const handleBack = () => {
        setActiveStep(activeStep - 1);
    };

    const secureKey = window.secureKey;
    const { data, error, loading } = useQuery(CART, {
       variables: {secureKey}
    });

    console.log(loading);
    console.log(error);
    if (loading) {
        return <div>loading...</div>;
    }
    else {
        console.log(data);
    }
    return (
        <React.Fragment>

            <CssBaseline />
            <AppBar position="absolute" color="default" >
                <Toolbar>
                    <Typography variant="h6" color="inherit" noWrap>
                        Badals.com Secure Checkout
                    </Typography>
                </Toolbar>
            </AppBar>
            <main>
                <ContainerGrid container>
                    <Grid item xs={12} md={12}>
                        <Stepper activeStep={state.step}>
                            {steps.map(label => (
                                <Step key={label}>
                                    <StepLabel>{label}</StepLabel>
                                </Step>
                            ))}
                        </Stepper>
                    </Grid>

                <RootGrid container spacing={0}>
            <RightGrid item xs={7} >
                <Grid item xs={12} md={12} >

                <InfoContainer>
                    <React.Fragment>
                        {getStepContent(state.step, data, dispatch)}
                        <ButtonDiv>
                            {activeStep !== 0 && (
                                <NavButton onClick={handleBack}>
                                    Back
                                </NavButton>
                            )}
                        </ButtonDiv>
                    </React.Fragment>
                </InfoContainer>
                </Grid>
            </RightGrid>
            <LeftGrid item xs={5}>

                    <Wrapper>
                      <CartSummary products={data.cart.items} carrier={state.carrier}/>
                    </Wrapper>

            </LeftGrid>

        </RootGrid>
                </ContainerGrid>
            </main>
        </React.Fragment>
    );
};
export default withApollo(App);