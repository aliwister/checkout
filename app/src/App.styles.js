// Create a <Title> react component that renders an <h1> which is
// centered, palevioletred and sized at 1.5em
import styled from "styled-components";
import {Container, Grid, Paper, Button} from "@material-ui/core";

export const Title = styled.h1`
  font-size: 1.5em;
  text-align: center;
  color: palevioletred;
`;

export const RootGrid = styled(Grid)`
  height: 100vh;
  width: 100%;
`;

export const RightGrid = styled(Grid)`
  height: 100%;
  width: 100%;
`;

export const LeftGrid = styled(Grid)`
  height: 100%;
  width: 100%;
  background-color: papayawhip;
`;

export const InfoContainer = styled(Container)`
  padding-top: ${ props  =>  props.theme.spacing(8)}px;
  padding-left: ${ props  =>  props.theme.spacing(20)}px !important;
`;

export const CheckoutPaper = styled(Paper)`

  padding: ${ props  =>  props.theme.spacing(8)}px;
  margin-top: ${ props  =>  props.theme.spacing(2)}px;
`;

// Create a <Wrapper> react component that renders a <section> with
// some padding and a papayawhip background
export const Wrapper = styled.section`
  padding: 4em;
  background: papayawhip;
`;

export const NavButton = styled(Button)`
  marginTop: ${ props  =>  props.theme.spacing(3)}px;
  marginLeft: ${ props  =>  props.theme.spacing(1)}px;
`;

export const ButtonDiv = styled.div`
  display: 'flex';
  justifyContent: 'flex-end';
`;