from flask import Flask
app = Flask(__name__)

@app.route("/")
def hello():
    return "Hello World!"

@app.route("/subject")
def subject():
    return "Subject"

@app.route("/verb")
def verb():
    return "Verb"

@app.route("/object")
def object():
    return "Object"

if __name__ == "__main__":
    app.run()
