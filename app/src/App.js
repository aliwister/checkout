import React, { useEffect, useReducer, useState } from 'react';
import 'react-bulma-components/dist/react-bulma-components.min.css';

import CssBaseline from '@material-ui/core/CssBaseline';
import Step from "@material-ui/core/Step";
import Stepper from "@material-ui/core/Stepper";
import StepLabel from "@material-ui/core/StepLabel";


// import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { useCookies } from 'react-cookie';
import Grid from "@material-ui/core/Grid";

import { InfoStep } from './components/InfoStep/InfoStep';
import { PaymentStep } from './components/PaymentStep';
import { CarrierStep } from './components/CarrierStep';
import { CartSummary } from './components/Cart/CartSummary';
import { CartCheckout } from './components/cart-checkout/CartCheckout';


import { useQuery } from '@apollo/react-hooks';
import { withApollo } from "react-apollo";
import HttpsIconSharp from '@material-ui/icons/Https';
import ArrowBackSharpIcon from '@material-ui/icons/ArrowBackSharp';
import CloseIcon from '@material-ui/icons/Close';

import {
  RootGrid,
  InfoContainer,
  RightGrid,
  LeftGrid,
  CartButton,
  NavButton,
  ButtonDiv,
  ContainerGrid,
  LogoImage,
  LogoWrapper,
  HelpPageWrapper,
  HelpPageContainer,
  Header,
} from './App.styles'
import { CART } from './graph/cart';
import Logoimage from './assets/logo.svg';
import Hidden from "@material-ui/core/Hidden";
import Drawer from "@material-ui/core/Drawer";
import IconButton from "@material-ui/core/IconButton";
import ShoppingBasketIcon from '@material-ui/icons/ShoppingBasket';
import CircularProgress from "@material-ui/core/CircularProgress";
import Backdrop from "@material-ui/core/Backdrop";
import {faAngleLeft, faShoppingCart} from "@fortawesome/fontawesome-free-solid";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {ButtonsRowContainer, ReturnToCartButton} from "./components/InfoStep/InfoStep.style";
import {Icon} from "react-bulma-components";

const steps = ['Shipping address', 'Payment details', 'Review your order'];
function reducer(state, action) {
  console.log(state,action)
  switch (action.type) {
    case 'NEXT': 
      return {
        ...state,
        step: state.step + 1
      }
    case 'SAVE_INFO':
      return {
        ...state,
        step: state.step + 1,
        cart: {
          ...state.cart,
          ...action.payload
        }
      }
    case 'PREV':
      return { state, step: state.step - 1 };
    case 'SET_CARRIER':
      return {
        ...state,
        carrier: action.payload
      }
    default:
      throw new Error('Unknown step');
  }

}

function getStepContent(step, dataAsState, dispatch, setCarrier) {
  //console.log(step);
  switch (step) {
    case 0:
      return <InfoStep state={dataAsState} dispatch={dispatch} />;
    case 1:
      return <CarrierStep state={dataAsState} dispatch={dispatch} />;
    case 2:
      return <PaymentStep state={dataAsState} dispatch={dispatch} />;
    default:
      throw new Error('Unknown step');
  }
}

const App = (props) => {
  const initialState = {
    step: 0,
    carrier: false
  }

  const steps = ['Customer Information', 'Select Carrier', 'Select Payment']
  const secureKey = window.secureKey;
  const guid = window.guid;

  const [state, dispatch] = useReducer(reducer, initialState);
  const [activeStep, setActiveStep] = React.useState(0);
  const [carrier, setCarrier] = useState();
  const { client } = props;
  const [cookies, setCookie, removeCookie] = useCookies(['nonce']);

  useEffect(() => {
    setCookie("nonce", guid, { path: "/", maxAge: 60 * 15 });
  }, []);



  const [drawer, setDrawer] = useState(false);

  const toggleDrawer = (open) => (event) => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
      return;
    }
    setDrawer(open);
  };

  const handleNext = () => {
    setActiveStep(activeStep + 1);
  };

  const handleBack = () => {
    setActiveStep(activeStep - 1);
  };


  const { data, loading, error } = useQuery(CART, {
    variables: { secureKey: secureKey || '' },
    fetchPolicy: "network-only"
  });

  if (loading || (error && !error.networkError && error.message === 'GraphQL error: No value present')) {
    return <Backdrop open={loading} >
      <CircularProgress color="inherit" />
    </Backdrop>;
  }  else if (error) {
    return (
      <div>
        <a href="https://www.badals.com">
          <ArrowBackSharpIcon />
        </a>
        <div>Failure</div>
      </div>
    )
  }

  return (
    <>
      <CssBaseline />
      <Header>
        <LogoWrapper> <LogoImage src={Logoimage} /> </LogoWrapper>
        <Hidden smDown>
          <div style={{ width: '100%', float: 'right' }}>
            <Typography variant="h6" color="inherit" noWrap style={{ float: 'right' }}>
              <HttpsIconSharp />
              Secure Checkout
            </Typography>
          </div>
        </Hidden>
      </Header>

      <HelpPageWrapper>
        <HelpPageContainer>
          <ContainerGrid container>
            <Grid item xs={12} md={12}>
              <Stepper activeStep={state.step}>
                {steps.map(label => (
                  <Step key={label}>
                    <StepLabel><Hidden xsDown>{label}</Hidden></StepLabel>
                  </Step>
                ))}
              </Stepper>
            </Grid>

            <RootGrid container spacing={0}>
              <RightGrid item md={7} sm={12} >
                <Grid item xs={12} md={12} >
                  {/*                    <a href="https://www.badals.com">
                        <ArrowBackSharpIcon/>
                    </a>*/}
                  <Hidden mdUp>
                    <div style={{ textAlign: 'center' }}>
                      <CartButton onClick={toggleDrawer(true)}>
                        <Icon>
                          <FontAwesomeIcon icon={faShoppingCart} />
                        </Icon>
                        &nbsp;&nbsp;&nbsp;Show Cart
                      </CartButton>

                    </div>
                    <Drawer anchor="right" open={drawer} onClose={toggleDrawer(false)}>
                      <div>
                        <span> <IconButton onClick={toggleDrawer(false)} ><CloseIcon /></IconButton></span>
                        <CartSummary products={data.cart.items} carrier={state.carrier} />
                      </div>
                    </Drawer>
                  </Hidden>
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
              <Hidden smDown>
                <LeftGrid item md={5} xs={12}>
                  {/* <Wrapper>
                    <CartSummary products={data.cart.items} carrier={state.carrier} />
                  </Wrapper> */}
                  <CartCheckout products={data.cart.items} carrier={state.carrier} step={state.step} />
                </LeftGrid>
              </Hidden>

            </RootGrid>
          </ContainerGrid>
        </HelpPageContainer>
      </HelpPageWrapper>
    </>
  );
};

export default withApollo(App);
