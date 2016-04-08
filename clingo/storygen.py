#storygen.py


import gringo

prg = gringo.Control()
prg.load("story.lp")
prg.ground([("base", [])])
prg.solve(None, on_model)