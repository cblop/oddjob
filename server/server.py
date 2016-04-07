from flask import Flask, jsonify
from tropes import get_chars, get_archetypes, get_tropes
app = Flask(__name__)

@app.route("/")
def hello():
    return "Hello World!"

@app.route("/tropes/")
def tropes():
    tropelist = get_tropes()
    tropedict = {'tropes': tropelist}
    return jsonify(**tropedict)

@app.route("/archetypes/")
def archetypes():
    archlist = get_archetypes()
    archdict = {'archetypes': archlist}
    return jsonify(**archdict)

@app.route("/characters/")
def characters():
    charlist = get_chars()
    chardict = {'characters': charlist}
    return jsonify(**chardict)

if __name__ == "__main__":
    app.run(debug=True)
