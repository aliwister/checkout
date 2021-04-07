import styled from "styled-components";
import {Button, Container, Heading, Form} from "react-bulma-components";
const { Checkbox, Field, Control, Radio } = Form;

export const InfoDiv = styled.div`
  //padding: ${props => props.theme.spacing(8)}px;
  // margin-bottom: ${props => props.theme.spacing(4)}px;
`;

export const RadioDiv = styled.div`
   width : 100%;
   border: 1px solid #e6e6e6;
   padding: 5px;
   padding-left: 15px;
`;

export const HeadingInformation = styled(Heading)`
    margin-bottom: 15px;
    margin-top: 40px;
`;

export const CheckControl = styled(Control)`
  margin-top: 15px;
  label {
    height: auto;
    display: flex;
    flex-direction: row;
    align-items: center;
    font-weight: 300;
  }
`;

export const RadioWapper = styled(Radio)`
    height: auto;
    display: flex; 
    flex-direction: row; 
    align-items: center; 
    font-weight: 400; 
    font-size: 15px;
`;

export const ButtonsRowContainer = styled(Container)`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin-top: 20px;
  margin-bottom: 20px;
`;

export const ReturnToCartButton = styled(Button)`
  border: none;
  display: flex;
  flex-direction: row;
  align-items: center;
  color: #999999;
  padding: 0px;
`;



export const LoginButtonWrapper = styled.p`
  color: #999999;
  display: flex;
  flex-direction: row;
  align-items: center;
`;

export const LoginLinkButton = styled.span`
  color: #bae8ff;
  font-size: 14px;
  cursor: pointer;
  :hover {
    color: #33bbff;
  }
`;