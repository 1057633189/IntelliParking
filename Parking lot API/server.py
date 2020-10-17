import json
import os

import web

urls = (
    '/(.*)parkingLotInfo', 'ParkingLotInfo'
)

app = web.application(urls, globals())


class ParkingLotInfo:
    def POST(self, *args):
        parking_lot_name = json.loads(web.data().decode()).get("name")
        # API
        file_path = os.path.join('.', 'Parking lot', parking_lot_name, 'parking lot.json')
        with open(file_path, 'r') as file:
            return json.load(file)


if __name__ == '__main__':
    app.run()
