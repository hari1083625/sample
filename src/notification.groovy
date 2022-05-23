print("Loaded class notification.groovy")

String notifyMethod() {
        office365ConnectorWebhooks([[
                    startNotification: true,
                    notifySuccess: true,
                    notifyFailure: true,
                    notifyAborted: true,
                    notifyBackToNormal: true,
                    notifyNotBuilt: true,
                    notifyRepeatedFailure: true,
                        url: '''https://mindtreeonline.webhook.office.com/webhookb2/329f59da-c0a6-4edb-b0a1-cbd712509488@85c997b9-f494-46b3-a11d-772983cf6f11/IncomingWebhook/716048a3dbcb4ebebc91cdbbf1c536a1/961ab056-0929-4c45-9d67-de9017c84fb0'''
            ]]
        )
}

return this
