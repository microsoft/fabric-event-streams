# flightinfomock
This is used to share the javascript to simulate the flights information with Logic App on Azure. This script will be invoked by Recurrence to produce the content which will be sent to Event Hub or Eventstream.

The whole steps in Logic App (with stateful workflow) are:

![flightinfomock flow](https://github.com/xujxu/flightinfomock/assets/68268054/df818630-0cb7-443f-84c0-bc8bea8e6ed9)

- Recurrence
- Execute JavaScript Code (copy the script content to this step)
- Send Event (Event Hub connector In-App)
  - Add the EH connection:
    ![EH connection](https://github.com/xujxu/flightinfomock/assets/68268054/bdccad1e-7b77-478c-9808-a6c13c91dc7a)

  - Configure the EH step:
    ![EH configuration](https://github.com/xujxu/flightinfomock/assets/68268054/19ae90e8-6c96-40d8-bc05-24b77daeb17c)


    


