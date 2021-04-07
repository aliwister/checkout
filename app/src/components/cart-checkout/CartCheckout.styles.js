import styled from "styled-components";

import Heading from 'react-bulma-components/lib/components/heading';
import Container from 'react-bulma-components/lib/components/container';
import Button from 'react-bulma-components/lib/components/button';
import Section from 'react-bulma-components/lib/components/section';
import Image from 'react-bulma-components/lib/components/image';

import Control from 'react-bulma-components/lib/components/form/components/control';
import Field from 'react-bulma-components/lib/components/form/components/field';


export const CartCheckoutSection = styled(Section)`
    width: 100%;
    padding: 0px 20px;
`;

export const ProductsSection = styled(Section)`
  width: 100%;
  padding: 10px 0px;
  border-bottom: 1px solid #999999;
`;

export const ProductContainer = styled(Container)`
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-center: space-between;
`;

export const ProductImageContainer = styled.div`
  position: relative;
`;

export const ProductImage = styled(Image)`
  width: 70px;
  height: 70px;
  border-radius: 10px;
  overflow: hidden;
`;

export const ProductsNumberBadge = styled.span`
  position: absolute;
  right: 0;
  top: 0;
  width: 20px;
  height: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50px;
  background-color: #999999;
  opacity: 0.9;
  color: white;
  margin-top: -6px;
  margin-right: -6px;
`;

export const ProductHeadingContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 10px;
`;

export const ProductHeading = styled(Heading)`
  font-size: 13px;
  width: 145px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export const ProductPrice = styled.p`
  font-size: 13px;
  margin-left: auto;
  font-weight: 600;
`;

export const CardInputFormField = styled(Field)`
  padding: 20px 0px 30px 0px;
  border-bottom: 1px solid #999999;
  margin: 0px !important;
`;

export const CardInputForm = styled(Control)`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  Justify-content: space-between;
`;

export const ApplyButton = styled(Button)`
  margin-left: 10px;
  background-color: #c8c8c8;
  color: white;
  :hover {
    background-color: #999999;
    color: white;
  }
`;

export const ProductInfosSection = styled(Section)`
  width: 100%;
  padding: 10px 0px;
  border-bottom: 1px solid #999999;
`;

export const ProductInfoRowContainer = styled(Container)`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: ${props => props.padding ? props.padding : "5px 0px"};
`;

export const ProductInfoTitle = styled(Heading)`
  font-size: 15px;
  font-weight: 300;
  margin: 0px !important;
`;

export const ProductInfoSubTitle = styled(Heading)`
  font-size: ${props => props.fontSize ? props.fontSize : "13px"};
  font-weight: ${props => props.fontWeight ? props.fontWeight : "300"};
  margin: 0px !important;
`;
