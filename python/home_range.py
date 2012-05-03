#!/usr/bin/env python
# Time-stamp: <2012-05-03 13:38:19 (bm3719)>

"""
   home-range: Describes a preliminary definition of "home range" and returns
   that for all specified ships in the given input AIS 3.0 input
   data.  Requires a geohashing library in maplib.
"""

import unittest
import maplib

def home_range(registry, record_generator_f):
    """Given a ship registry and a function that returns AIS 3.0 data, return a
    home range for all ships in the registry."""
    hr = {}
    for mmsi in registry.keys():
        ship = registry[mmsi]
        hr[mmsi] = {
            'ship': ship,
            'gh_wt': {} }
    for record in record_generator_f():
        mmsi = record['mmsi']
        if mmsi in registry.keys():
            # TODO: Error check lat/lon.  Skip record if not valid floats.
            geohash = maplib.hash(float(record['lat']), float(record['lon']))
            # Trim geohash, which will bucket by a larger box.
            geohash = geohash[:-6]
            # Aggregate a simple count within each geohash for now.  If we want
            # a real weight here, add some kind of calculation based upon an
            # estimate of time spent within it.
            if geohash in hr[mmsi]['gh_wt']:
                hr[mmsi]['gh_wt'][geohash] += 1
            else: 
                hr[mmsi]['gh_wt'][geohash] = 1
    return hr

class TestHomeRange(unittest.TestCase):
    """Tests home range function(s)."""
    def setUp(self):
        """Loads ship data and informs test runner how many ships were
        specified."""
        self.ships = {}
        self.records = []
        for test_ship in SHIP_TEST_DATA:
            (mmsi, name, imo, callsign, status, ship_class, width_m, draft_m,
             former_names, year, flag, grt, dwt, speed, teu, pax,
             official_number, owners, management, builder, yard, sales,
             accidents) = test_ship
            self.ships[mmsi] = {
                'name': name,
                'imo': imo,
                'callsign': callsign,
                'status': status,
                'ship_class': ship_class,
                'width_m': width_m,
                'draft_m': draft_m,
                'former_names': former_names,
                'year': year,
                'flag': flag,
                'grt': grt,
                'dwt': dwt,
                'speed': speed,
                'teu': teu,
                'pax': pax,
                'official_number': official_number,
                'owners': owners,
                'management': management,
                'builder': builder,
                'yard': yard,
                'sales': sales,
                'accidents': accidents }
        print 'Test setup complete.  ' + str(len(self.ships.keys())) + \
            ' ship records loaded.'

    def test_home_range(self):
        """Tests the home_range function."""
        def test_record_generator():
            """A generator method that lazy loads records from a datasource.
            Unnecessary for this test, but an idiom like this is useful when
            reading in massive amounts of data."""
            for test_data in AIS_TEST_DATA:
                (datasource, mmsi, name, ship_type, lat, lon, speed_kph,
                 heading, time_gmt, destination, eta_gmt) = test_data
                yield {'datasource': datasource,
                       'mmsi': mmsi,
                       'name': name,
                       'type': ship_type,
                       'lat': lat,
                       'lon': lon,
                       'speed_kph': speed_kph,
                       'heading': heading,
                       'time_gmt': time_gmt,
                       'destination': destination,
                       'eta_gmt': eta_gmt }

        result = home_range(self.ships, test_record_generator)
        expected_result = {
            'u115fx': 1,
            'u1178r': 1,
            'u115zh': 1,
            'u115vg': 1,
            'u115gr': 2,
            'u115ut': 1,
            'u115yc': 1,
            'u117cn': 2 }
        self.assertEqual(result['210981000']['gh_wt'], expected_result)

if __name__ == '__main__':
    global AIS_TEST_DATA
    global SHIP_TEST_DATA
    AIS_TEST_DATA = [
        ['ais(3.0)', '210981000', 'GARDENIA', '', '51.32052', '1.803267', '14.8',
         '269.0', '1203220340', 'OOST/RAMS/OOST', '1203220530'],
        ['ais(3.0)', '210981000', 'GARDENIA', '', '51.32052', '1.803267', '14.8',
         '269.0', '1203220340', 'OOST/RAMS/OOST', '1203220530'],
        ['ais(3.0)', '210981000', 'GARDENIA', '', '51.27934', '1.777718', '15.1',
         '287.0', '1203221555', 'OOST/RAMS/OOST', '1203221730'],
        ['ais(3.0)', '210981000', 'GARDENIA', '', '51.29249', '1.712535', '15.3',
         '290.0', '1203221605', 'OOST/RAMS/OOST', '1203221730'],
        ['ais(3.0)', '210981000', 'GARDENIA', '', '51.304', '1.659172', '14.7',
         '288.0', '1203221614', 'OOST/RAMS/OOST', '1203221730'],
        ['ais(3.0)', '210981000', 'GARDENIA', '', '51.31475', '1.605293', '15.0',
         '288.0', '1203221622', 'OOST/RAMS/OOST', '1203221730'],
        ['ais(3.0)', '210981000', 'GARDENIA', '', '51.32515', '1.55427', '14.6',
         '289.0', '1203221630', 'OOST/RAMS/OOST', '1203221730'],
        ['ais(3.0)', '210981000', 'GARDENIA', '', '51.32515', '1.55427', '14.6',
         '289.0', '1203221630', 'OOST/RAMS/OOST', '1203221730'],
        ['ais(3.0)', '210981000', 'GARDENIA', '', '51.32721', '1.52604', '12.8',
         '83.0', '1203221858', 'OOST/RAMS/OOST', '1203221730'],
        ['ais(3.0)', '210981000', 'GARDENIA', '', '51.30971', '1.720623', '13.7',
         '100.0', '1203221930', 'OOST/RAMS/OOST', '1203230030']]
    SHIP_TEST_DATA = [['210981000', 'GARDENIA', '', 'P3JY9', '', 'Other', '20.0',
                       '', 'CGM Oyapock-97-', '1984', 'Antigua & Barbuda',
                       '8853', '12077', '16', '', '', '0',
                       'Partenreederei ms Germania', 'Gerd Koppelmann',
                       'J.J. Sietas KG Schiffswerft GmbH &Co.- Hamburg',
                       '921', '', '']]
    unittest.main()
