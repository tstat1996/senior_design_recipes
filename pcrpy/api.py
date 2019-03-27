from flask import Flask, request
from flask_restful import Resource, Api
from pcrpy.surpriseRecommender import Recommender
from pcrpy.surpriseRecommender import RecType
from pcrpy.recs import courses


app = Flask(__name__)
api = Api(app)

class student_recs(Resource):
    def get(self):
        r = Recommender(10, RecType.COURSE_QUALITY).run_rec_alg()
        check = [i.strip().split("\t") for i in open('./recommendations.csv').readlines()]
        x = list()
        for stu in check:
            y = list()
            y.append("for student " + stu[0] + " we recommend:")
            for c in stu[1:]:
                for course in courses:
                    if c == course.get_id():
                        y.append(str(course.get_aliases()) + " " + course.get_name())
            x.append(y)
        return x


api.add_resource(student_recs, '/getrecs') # Route_1

if __name__ == '__main__':
    app.run(port='5002')