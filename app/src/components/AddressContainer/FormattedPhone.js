import React from "react";
import PhoneInput from 'react-phone-input-2';
import startsWith from "lodash.startswith";
import 'react-phone-input-2/lib/style.css';

export const FormattedPhone = ({mobile, setMobile}) => (<PhoneInput
    type='text'
    country={'om'}
    value={mobile}
    onChange={mobile => setMobile(mobile.replace(/[^0-9]+/g, ''))}
    onlyCountries={['om']}
    masks={{ om: '+... ....-....' }}
    copyNumbersOnly={true}
    containerStyle={{ direction: 'ltr' }}
    inputStyle={{ width: '100%', height: '40px', paddingRight: '48px' }}
    style={{ margin: '10px 0px' }}
    countryCodeEditable={false}
    isValid={(inputNumber, onlyCountries) => {
      return onlyCountries.some((country) => {
        return startsWith(inputNumber, country.dialCode) || startsWith(country.dialCode, inputNumber);
      });
    }}
/>);