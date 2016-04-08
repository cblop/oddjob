# storygen.py
# Thomas AE Smith 08/04/2016

import gringo

story = {}

def on_model(model):
	# scenes = 0
	for m in model.atoms():
		if m.name() == "storify":
			scene, action, text = m.args()
			story[(scene, action)] = text
			# scenes = max(scenes, scene)
		else:
			pass
			#print m
	# for s in range(1,scenes+1):
	# 	print story[(s,0)] + ". " + story[(s,1)] + ". " + story[(s,2)] + ". " + story[(s,3)] + " - " + story[(s,4)] + ".\n"

def storygen(characters):
	prg = gringo.Control()
	prg.load("story.lp")
	prg.ground([("base", [])])
	res = prg.solve(None, on_model)
	return res.model.atoms()

if __name__ == "__main__":
	storygen()