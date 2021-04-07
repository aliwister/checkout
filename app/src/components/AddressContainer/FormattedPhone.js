import React from "react";
import startsWith from "lodash.startswith";
import loadable from '@loadable/component'
const PhoneInput = loadable(() => import('react-phone-input-2'));


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