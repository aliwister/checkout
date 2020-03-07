import React, { Component } from 'react'
import './CkoFrames.css'

class CkoFrames extends Component {
  constructor (props) {
    super(props)
    this.state = {
      key: 'pk_932e59ef-2d33-448d-80cd-8668691640fe',
      token: '',
      errorMessage: '',
      showPaymentMethod: false,
      paymentMethodIcon: '',
      payButton: false
    }
    this.handleProcessPayment = props.handleProcessPayment;
  }

  // Initialise Frames and register the event handlers
  componentDidMount () {
    const context = this
    window.Frames.init(this.state.key)
    window.Frames.addEventHandler('cardValidationChanged', function (event) {
      context.validationChanged(event)
    })
    window.Frames.addEventHandler('paymentMethodChanged', function (event) {
      context.paymentMethodChanged(event)
    })
    window.Frames.addEventHandler('cardTokenized', function (event) {
      context.cardTokenised(event)
    })
  }

  // Handle tokenisation
  cardTokenised (event) {
    this.setState({
      token: event
    })

    /*alert(
      'Token: ' +
        event.token +
        '\nFull response in the console or in state.token'
    )*/
    return this.handleProcessPayment(event.token);
    console.log(event)
  }

  // Handle card input validation changes
  validationChanged () {
    // enable or disable the payment button based on the validity of the card input
    this.setState({
      payButton: !window.Frames.isCardValid()
    })
  }

  // Handle card type detection
  paymentMethodChanged (event) {
    var pm = event.paymentMethod
    if (!pm) {
      this.setState({
        showPaymentMethod: false
      })
    } else {
      this.setState({
        paymentMethodIcon: pm.toLowerCase(),
        showPaymentMethod: true
      })
    }
  }

  // Handle form submission
  handleSubmit (event) {
    event.preventDefault()
    window.Frames.submitCard()
  }

  render () {
    return (
      <div className='App'>
        <form id='payment-form' onSubmit={this.handleSubmit}>
          <label htmlFor='card-number'>Card number</label>
          <div className='input-container card-number'>
            <div className='icon-container'>
              <img
                id='icon-card-number'
                src={require(`./card-icons/card.svg`)}
                alt='PAN'
              />
            </div>
            <div className='card-number-frame' />
            {/* Display the payment icon only when needed */}
            {this.state.showPaymentMethod ? (
              <div className='icon-container payment-method'>
                <img
                  id='logo-payment-method'
                  alt='payment method'
                  src={require(`./card-icons/${
                    this.state.paymentMethodIcon
                  }.svg`)}
                />
              </div>
            ) : null}
          </div>

          <div className='date-and-code'>
            <div>
              <label htmlFor='expiry-date'>Expiry date</label>
              <div className='input-container expiry-date'>
                <div className='icon-container'>
                  <img
                    id='icon-expiry-date'
                    src={require(`./card-icons/exp-date.svg`)}
                    alt='Expiry date'
                  />
                </div>
                <div className='expiry-date-frame' />
              </div>
            </div>

            <div>
              <label htmlFor='cvv'>Security code</label>
              <div className='input-container cvv'>
                <div className='icon-container'>
                  <img
                    id='icon-cvv'
                    src={require(`./card-icons/cvv.svg`)}
                    alt='CVV'
                  />
                </div>
                <div className='cvv-frame' />
              </div>
            </div>
          </div>

          <button
            id='pay-button'
            type='submit'
            disabled={this.state.payButton}
            onClick={this.submitFrames}
          >
            PAY
          </button>

          <div>
            <span className='error-message error-message__card-number' />
            <span className='error-message error-message__expiry-date' />
            <span className='error-message error-message__cvv' />
          </div>

          <p className='success-payment-message' />
        </form>

      </div>
    )
  }
}

export default CkoFrames
