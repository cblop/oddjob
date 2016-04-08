# storygen.py
# Thomas AE Smith 08/04/2016

import gringo

def storygen(characters):
	prg = gringo.Control()
	prg.load("story.lp")
	prg.ground([("base", [])])
	prg.solve(None, on_model)

if __name__ == "__main__":
	storygen()
