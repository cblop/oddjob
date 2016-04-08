# storygen.py
# Thomas AE Smith 08/04/2016

import gringo

story = {}
scenes = 0

def on_model(model):
    global scenes
    scenes = 0
    global story
    story = {}
    print("callback")
    for m in model.atoms():
        if m.name() == "storify":
            print("storify")
            scene, action, text = m.args()
            story[str((scene, action))] = [(scene, action), text]
            scenes = max(scenes, scene)
        else:
            print "what"
            pass
    print story
    # for s in range(1,scenes+1):
	# 	print story[(s,0)] + ". " + story[(s,1)] + ". " + story[(s,2)] + ". " + story[(s,3)] + " - " + story[(s,4)] + ".\n"

def storygen(characters):
    prg = gringo.Control()
    prg.load("story.lp")
    prg.ground([("base", [])])
    res = prg.solve(None, on_model)
    print("solving...")
    print(story)
    while True:
        if story:
            if (len(story) / 5) == scenes:
                return story
    # return story


if __name__ == "__main__":
	storygen()
