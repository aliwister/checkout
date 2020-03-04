import React from 'react';
import { render } from 'react-dom';
import { createMuiTheme} from '@material-ui/core/styles';
import { ThemeProvider } from 'styled-components';

import ApolloClient from 'apollo-boost';
import { ApolloProvider } from '@apollo/react-hooks';
import App from './App';

const theme = createMuiTheme();

const client = new ApolloClient({
    uri: 'http://127.0.0.1:3003/graphql',
});

render((
    <ThemeProvider theme={theme}>
        <ApolloProvider client={client}>
            <App/>
        </ApolloProvider>
    </ThemeProvider>
), document.getElementById('index'));

