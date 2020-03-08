import React from 'react';
import { render } from 'react-dom';
import { createMuiTheme} from '@material-ui/core/styles';
import { ThemeProvider } from 'styled-components';

import ApolloClient from 'apollo-boost';
import { ApolloProvider } from '@apollo/react-hooks';
import App from './App';
console.log(process.env.REACT_APP_API_URL);
const theme = createMuiTheme();


const client = new ApolloClient({
    uri: process.env.REACT_APP_API_URL,
});

render((
    <ThemeProvider theme={theme}>
        <ApolloProvider client={client}>
            <App/>
        </ApolloProvider>
    </ThemeProvider>
), document.getElementById('index'));

