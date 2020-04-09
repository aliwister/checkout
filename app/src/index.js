import React from 'react';
import { render } from 'react-dom';
import { createMuiTheme} from '@material-ui/core/styles';
import { ThemeProvider } from 'styled-components';
import { IconContext } from "react-icons";
import ApolloClient from 'apollo-boost';
import { ApolloProvider } from '@apollo/react-hooks';
import App from './App';

const theme = createMuiTheme();


const client = new ApolloClient({
    uri: process.env.REACT_APP_API_URL,
});

render((
    <ThemeProvider theme={theme}>
        <ApolloProvider client={client}>
            <IconContext.Provider value={{style:{ color: "blue", verticalAlign: 'middle' }, size:"1.5em"}}>
                <App/>
            </IconContext.Provider>
        </ApolloProvider>
    </ThemeProvider>
), document.getElementById('index'));

