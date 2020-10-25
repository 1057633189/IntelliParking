import json
import os

import web

urls = (
    '/(.*)postReservationInfo', 'PostReservationInfo',
    '/(.*)getReservationInfo', 'GetReservationInfo',
    '/(.*)reservation', 'Reservation'
)

app = web.application(urls, globals())


class PostReservationInfo:
    def POST(self, *args):
        i = json.loads(web.data().decode())
        parking_lot_name = i.get('parking lot name')
        address = i.get('address')
        available_parking_spaces = i.get('available parking spaces')
        price = i.get('price')
        available_time = i.get('available time')
        with open(os.path.join('.', 'available reservation database.json'), 'r') as file:
            j = json.loads(file.read())
        j.insert(0, {'parking lot name': parking_lot_name, 'address': address,
                     'available parking spaces': available_parking_spaces, 'price': price,
                     'available time': available_time})
        with open(os.path.join('.', 'available reservation database.json'), 'w') as file:
            json.dump(j, file, indent=4, separators=(',', ': '))
        return 'success'


class GetReservationInfo:
    def GET(self, *args):
        with open(os.path.join('.', 'available reservation database.json'), 'r') as file:
            return json.dumps(json.load(file)[0], indent=4, separators=(',', ': '))


class Reservation:
    def POST(self, *args):
        i = json.loads(web.data().decode())
        driver_name = i.get('driver name')
        phone_number = i.get('phone number')
        car_number = i.get('car number')
        booking_time = i.get('booking time')
        with open(os.path.join('.', 'available reservation database.json'), 'r') as file:
            j = json.loads(file.read())
        aps = j[0]['available parking spaces']
        aps = str(int(aps) - 1)
        j[0]['available parking spaces'] = aps
        price = int(j[0]['price'].split(' ')[0])
        parking_lot_name = j[0]['parking lot name']
        parking_lot_address = j[0]['address']
        with open(os.path.join('.', 'available reservation database.json'), 'w') as file:
            json.dump(j, file, indent=4, separators=(',', ': '))
        split = booking_time.split(' ')[1].split('-')
        begin = int(split[0].split(':')[0])
        end = int(split[1].split(':')[0])
        booking_hour = end - begin
        total_price = price * booking_hour

        with open(os.path.join('.', 'reservation order database.json'), 'r') as file:
            j = json.loads(file.read())
        j.insert(0, {'parking lot name': parking_lot_name, 'parking lot address': parking_lot_address,
                     'driver name': driver_name, 'driver phone number': phone_number, 'driver car number': car_number,
                     'total price': str(total_price), 'booking time': booking_time})
        with open(os.path.join('.', 'reservation order database.json'), 'w') as file:
            json.dump(j, file, indent=4, separators=(',', ': '))
        return 'success'


if __name__ == '__main__':
    app.run()
