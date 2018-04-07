import requests
from haversine import haversine

from settings import get_env


class candidate_stops:
    def get_journey(self, start, end):
        token = self.authenticate_client(client_id=get_env("CLIENT_ID"),
                                         client_secret=get_env("CLIENT_SECRET"))

        url = get_env("JOURNEY_ENDPOINT")

        payload = "{\n    \"geometry\":{\n        \"type\":\"Multipoint\",\n        \"coordinates\":[\n          " + str(
            start) + "," + str(end) + " \n        ]\n    }\n}"

        headers = {
            'Content-Type': "application/json",
            'Authorization': "Bearer " + token,
            'Accept': "application/json",
            'Cache-Control': "no-cache"
        }

        response = requests.request("POST", url, data=payload, headers=headers)

        itineraries = response.json()["itineraries"]
        for each_itenerary in itineraries:
            for each_leg in each_itenerary['legs']:

                if each_leg['type'] == "Transit":
                    geometry_points = each_leg['geometry']['coordinates']
                    #print(geometry_points)
                    #print(self.get_distances_between_adjacent_points(geometry_points))
                    print(self.get_distances_between_start_and_point(geometry_points))

                    #self.add_stops(each_leg['line']['id'], each_leg['geometry']['coordinates'][0],
                                   #each_leg['geometry']['coordinates'][len(each_leg['geometry']) - 1])
                    #pass

    def get_distances_between_adjacent_points(self, points):
        distances = []
        for i in range(1, len(points)):
            start = (points[i - 1][0], points[i - 1][1])
            stop = (points[i][0], points[i][1])
            distances.append(haversine(start, stop) * 1000)
        return distances

    def get_distances_between_start_and_point(self, points):
        distances = []
        start = points[0]
        for i in range(1, len(points)):

            stop = (points[i][0], points[i][1])
            distances.append(haversine(start, stop) * 1000)
        return distances

    #def add_stops(self, line_id, start, end):
        #return
        # print(geopy.distance.vincenty(start, end).km)

    def authenticate_client(self, client_id, client_secret):
        url = get_env("AUTHENTICATE_ENDPOINT")

        payload = {
            "client_id": client_id,
            "client_secret": client_secret,
            "grant_type": "client_credentials",
            "scope": "transitapi:all"
        }
        headers = {
            'Content-Type': "application/x-www-form-urlencoded",
            'Accept': "application/json",
            'Cache-Control': "no-cache",
            'Postman-Token': "425ef144-f0ea-47b9-a502-c9c949063725"
        }

        response = requests.request("POST", url, data=payload, headers=headers)

        # print(response)
        token = response.json()["access_token"]

        return token


if __name__ == '__main__':
    object_candidate_stops = candidate_stops()
    object_candidate_stops.get_journey([ 36.80885420000004, -1.2695587], [36.94873329999996, -1.1961911])
