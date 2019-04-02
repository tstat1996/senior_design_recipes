from flask import Flask, request
from flask_restful import Resource, Api
import json
from surpriseRecommender import Recommender
from surpriseRecommender import RecType
from recs import courses


app = Flask(__name__)
api = Api(app)

## TODO: add ability to take in user input and send back recs
class student_recs(Resource):
    def get(self):
        args = request.args
        course1 = args['course1']
        course2 = args['course2']
        course3 = args['course3']
        course4 = args['course4']
        course5 = args['course5']
        rating1 = args['rating1']
        rating2 = args['rating2']
        rating3 = args['rating3']
        rating4 = args['rating4']
        rating5 = args['rating5']
        r = Recommender(10, RecType.COURSE_QUALITY).run_rec_alg()
        check = [i.strip().split("\t") for i in open('./recommendations.csv').readlines()]
        x = {}
        for stu in check:
            y = list()
            for c in stu[1:]:
                for course in courses:
                    if c == course.get_id():
                        y.append(str(course.get_aliases()) + " " + course.get_name())
            x[stu[0]] = y
        return json.dumps(x)


api.add_resource(student_recs, '/getrecs') # Route_1

if __name__ == '__main__':
    app.run(port='5002')