// Create a <Title> react component that renders an <h1> which is
// centered, palevioletred and sized at 1.5em
import styled from "styled-components";
import { Container, Grid, Paper } from "@material-ui/core";

import { Button, Navbar } from 'react-bulma-components';

export const Header = styled(Navbar)`
  align-items: center;
  padding: 0 20px;
  height: 80px;
`

export const Title = styled.h1`
  font-size: 1.5em;
  text-align: center;
  color: palevioletred;
`;

export const RootGrid = styled(Grid)`
  width: 100%;
  padding:  ${ props  =>  props.theme.spacing(0)}px;
`;
export const ContainerGrid = styled(Grid)`
  height: 100vh;
  width: 100%;
  margin-top:  ${ props  =>  props.theme.spacing(0)}px;
`;
export const RightGrid = styled(Grid)`
  height: 100%;
  width: 100%;
`;

export const LeftGrid = styled(Grid)`
  height: 100%;
  width: 100%;
`;

export const InfoContainer = styled(Container)`
  padding-top: ${ props  =>  props.theme.spacing(1)}px;
  padding-left: ${ props  =>  props.theme.spacing(2)}px !important;
`;

export const CheckoutPaper = styled(Paper)`

  padding: ${ props  =>  props.theme.spacing(2)}px;
  margin-top: ${ props  =>  props.theme.spacing(2)}px;
`;

// Create a <Wrapper> react component that renders a <section> with
// some padding and a papayawhip background
export const Wrapper = styled.section`
  padding: ${ props  =>  props.theme.spacing(1)}px;;
`;

export const NavButton = styled(Button)`
  margin-top: ${ props  =>  props.theme.spacing(3)}px;
  margin-bottom: ${ props  =>  props.theme.spacing(3)}px;
  padding: 24px 20px;
  float: right;
  background-color: #373737;
  color: white;
  font-weight: 500;
  font-size: 13px;
  :hover {
    color: white;
    background-color: #404040;
  }
`;

export const ButtonDiv = styled.div`
  display: 'flex';
  justifyContent: 'flex-end';
`;

export const Address = styled.span`
  font-family: 'Lato', sans-serif;
  font-size: '15'px;
  font-weight:  '400';
  color: '#77798c';
  line-height: 1.5;
`;
export const LogoImage = styled.img`
  display: block;
  backface-visibility: hidden;
  max-width: 150px;
  text-align: center;
`;
export const LogoWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  padding: 50px 40px 35px 20px;
`;
export const Heading = styled.h3`
  font-size: 21px;
  font-weight: 700;
  color: #0d1136;
  line-height: 1.2;
  margin-bottom: 25px;
  width: 100%;
  text-align: center;
`;

export const HelpPageWrapper = styled.div`
  background-color: white;
  position: relative;
  padding: 10px 0 60px 0;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

`;

export const HelpPageContainer = styled.div`
  background-color: transparent;
  padding: 0;
  border-radius: 6px;

  position: relative;
  @media (min-width: 990px) {
    width: 950px;
    margin-left: auto;
    margin-right: auto;
  }

  @media (min-width: 400px) {
    padding: 30px;
  }
`;