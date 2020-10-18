import json
import os
from datetime import datetime

import web

urls = (
    '/(.*)parkingLotInfo', 'ParkingLotInfo',
    '/(.*)parkingReservation', 'ParkingReservation'
)

app = web.application(urls, globals())


class ParkingLotInfo:
    def POST(self, *args):
        parking_lot_name = json.loads(web.data().decode()).get("name")
        # API
        file_path = os.path.join('.', 'Parking lot', parking_lot_name, 'parking lot.json')
        with open(file_path, 'r') as file:
            return json.load(file)


class ParkingReservation:
    def POST(self, *args):
        i = json.loads(web.data().decode())
        parking_lot_name = i.get('name')
        parking_space = i.get('space')
        parking_reservation_time = i.get('time').split('--')
        # API
        file_path = os.path.join('.', 'Parking lot', parking_lot_name, 'parking lot.json')
        print(file_path)
        with open(file_path, 'r') as file:
            f = file.read()
            j = json.loads(f)
        with open(file_path, 'w') as file:
            reservation_list = j['reservation'][parking_space]
            if len(reservation_list) is 0:
                reservation_list.append('--'.join(parking_reservation_time))
                file.truncate()
                json.dump(j, file, sort_keys=True, indent=4, separators=(',', ':'))
                return 'success'
            else:
                for each_reservation in reservation_list:
                    begin_old = datetime.strptime(each_reservation.split('--')[0], '%Y-%m-%d %H:%M')
                    end_old = datetime.strptime(each_reservation.split('--')[1], '%Y-%m-%d %H:%M')
                    begin_new = datetime.strptime(parking_reservation_time[0], '%Y-%m-%d %H:%M')
                    end_new = datetime.strptime(parking_reservation_time[1], '%Y-%m-%d %H:%M')
                    if begin_new.__gt__(begin_old) and begin_new.__lt__(end_old):
                        json.dump(j, file, sort_keys=True, indent=4, separators=(',', ':'))
                        return 'error'
                    elif end_new.__gt__(begin_old) and end_new.__lt__(end_old):
                        json.dump(j, file, sort_keys=True, indent=4, separators=(',', ':'))
                        return 'error'
                    else:
                        reservation_list.append('--'.join(parking_reservation_time))
                        file.truncate()
                        json.dump(j, file, sort_keys=True, indent=4, separators=(',', ':'))
                        return 'success'


if __name__ == '__main__':
    app.run()
