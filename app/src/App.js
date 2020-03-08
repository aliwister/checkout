import React, {useEffect, useReducer} from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';

import { Container, Grid } from '@material-ui/core';

import { InfoStep } from './components/InfoStep';
import { PaymentStep } from './components/PaymentStep';
import { CarrierStep } from './components/CarrierStep';
import { CartSummary } from './components/Cart/CartSummary';

import {useMutation, useQuery} from '@apollo/react-hooks';
import { withApollo } from "react-apollo";

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
function reducer(state, action) {
    switch (action.type) {
        case 'NEXT':;
            return {step: state.step + 1}
        case 'PREV':
            return {step: state.count - 1};
        default:
            throw new Error('Unknown step');
    }

}

function getStepContent(step, state, dispatch) {
    console.log(step);
    switch (step) {
        case 0:
            return <InfoStep state={state} dispatch={dispatch}/>;
        case 1:
            return <CarrierStep state={state} dispatch={dispatch}/>;
        case 2:
            return <PaymentStep state={state} dispatch={dispatch}/>;
        default:
            throw new Error('Unknown step');
    }
}

const App = (props) => {
    const initialState = {
        step: 0
    }
    const [state, dispatch] = useReducer(reducer, initialState);
    const [spacing, setSpacing] = React.useState(5);
    const [activeStep, setActiveStep] = React.useState(0);
    const { client } = props;
    console.log(client);

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

    if (loading) {
        return <div>loading...</div>;
    }
    else {
        console.log(data);
    }
    return (
        <RootGrid container spacing={0}>
            <CssBaseline />
            <RightGrid item xs={7} >
                <Grid item xs={12} md={10} >
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
                <Grid item xs={12} md={10} >
                <Container component="main">
                    <Wrapper>
                      <CartSummary products={data.cart.items}/>
                    </Wrapper>
                </Container>
                </Grid>
            </LeftGrid>
        </RootGrid>
    );
};
export default withApollo(App);