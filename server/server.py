from flask import Flask, jsonify, request
# from flask.ext.transit import init_transit
# import sys
# sys.path.insert(0, '../clingo')
# sys.path.insert(0, '~/clingo')
import storygen
from tropes import get_chars, get_archetypes, get_tropes
app = Flask(__name__)

@app.route("/")
def hello():
    return "Hello World!"

tropelist = get_tropes()
archlist = get_archetypes()
charlist = get_chars()

@app.route("/story", methods = ["POST"])
def story():
    data = request.get_json()
    chars = data['chars']
    story = storygen.storygen(chars)
    return jsonify(**story)

@app.route("/tropes/")
def tropes():
    # tropelist = get_tropes()
    tropedict = {'tropes': tropelist}
    return jsonify(**tropedict)

@app.route("/archetypes/")
def archetypes():
    # archlist = get_archetypes()
    archdict = {'archetypes': archlist}
    return jsonify(**archdict)

@app.route("/characters/")
def characters():
    # charlist = get_chars()
    chardict = {'characters': charlist}
    return jsonify(**chardict)

if __name__ == "__main__":
    app.run(debug=True)
