import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';

import { Container, Grid } from '@material-ui/core';

import { InfoStep } from './components/InfoStep';
import { PaymentStep } from './components/PaymentStep';
import { CarrierStep } from './components/CarrierStep';
import { CartSummary } from './components/CartSummary';

import {useMutation, useQuery} from '@apollo/react-hooks';

import {RootGrid,
    InfoContainer,
    RightGrid,
    LeftGrid,
    Wrapper,
    Title,
    NavButton,
    ButtonDiv} from './App.styles'
import {CART} from './graph/cart';

import PaymentForm from "./components/old/PaymentForm";
import Review from "./components/old/Review";
import Button from "@material-ui/core/Button";

const steps = ['Shipping address', 'Payment details', 'Review your order'];
function getStepContent(step, data) {
    switch (step) {
        case 0:
            return <InfoStep data={data}/>;
        case 1:
            return <CarrierStep data={data}/>;
        case 2:
            return <PaymentStep data={data}/>;
        default:
            throw new Error('Unknown step');
    }
}


const App = () => {
    const [spacing, setSpacing] = React.useState(5);
    const [activeStep, setActiveStep] = React.useState(0);

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


    if (loading) {
        return <div>loading...</div>;
    }
    else {
        console.log(data);
    }


    return (
        <RootGrid container spacing={0}>
            <CssBaseline />
            <RightGrid item xs={7} justify="flex-end">
                <Grid item xs={12} md={10} >
                <InfoContainer>
                    <React.Fragment>
                        {getStepContent(activeStep, data)}
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
            <LeftGrid item xs={5} justify="flex-start">
                <Grid item xs={12} md={10} >
                <Container component="main">
                    <Wrapper>
                        {// <CartSummary cart={data.getCart.items}/>
                        }
                    </Wrapper>
                </Container>
                </Grid>
            </LeftGrid>
        </RootGrid>
    );
};

export default App;