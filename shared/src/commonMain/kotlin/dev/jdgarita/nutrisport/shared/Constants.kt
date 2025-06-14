package dev.jdgarita.nutrisport.shared

object Constants {
    const val WEB_CLIENT_ID = "example"

    const val MAX_QUANTITY = 10
    const val MIN_QUANTITY = 1

    const val PAYPAL_AUTH_ENDPOINT = "https://api-m.sandbox.paypal.com/v1/oauth2/token"
    const val PAYPAL_CHECKOUT_ENDPOINT = "https://api-m.sandbox.paypal.com/v2/checkout/orders"

    const val RETURN_URL = "dev.jdgarita.nutrisport://paypalpay?success=true"
    const val CANCEL_URL = "dev.jdgarita.nutrisport://paypalpay?cancel=true"
}