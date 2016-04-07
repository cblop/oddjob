#include "data.lp". 

1 { start(ID, 1, Init) : initial_action(Init,_,_,_)} 1 :- scene(ID, A, B, _, _).
1 { end(ID, Length, Close) : closing_action(Close,_,_)} 1 :- scene(ID, A, B, _, Length).

%% 1 { mid(ID, PrevAct+1, Act) : midpoints(Init,Act,Close,_,_) } 1 :- scene(ID, A, B, _, Length), mid(ID, PrevAct, _), PrevAct+1 < Length.
1 { action(ID, PrevAct+1, Act) : midpoints(Init,Act,Close,_,_) } 1 :- scene(ID, A, B, _, Length), start(ID, PrevAct, Init), end(ID, _, Close), PrevAct+1 < Length.

action(ID, ActNo, Act) :- start(ID, ActNo, Act).
action(ID, ActNo, Act) :- end(ID, ActNo, Act).
:- action(ID, _, Action), action(ID2, _, Action), ID < ID2.

charInScene(ID, X) :- scene(ID, X, _, _, _).
charInScene(ID, X) :- scene(ID, _, X, _, _).
char(X) :- charInScene(_, X).

:- scene(ID, X, Y, _, _), X = Y.

intro(ID, X) :- charInScene(ID, X), not charInScene(IDprev, X), IDprev < ID, scene(IDprev,_,_,_,_).

scene(1, "Alice", "Bob", pp, 3).
scene(2, "Alice", "Bob", pp, 3).
scene(3, "Alice", "Eve", pp, 3).
%% sceneDef(ID, A, B, PlotPoint, Length).
%% sceneInfo(ID, A, B).

%% storyDef(SceneCount).
%% storyInfo(StartScene, EndScene, SceneCount).

1{ storify(ID, 0, @chars(Text,A,B)) : initial_action(Init, _, _, Text) } 1 :- start(ID,1,Init), scene(ID, A, B, _, _).
1{ storify(ID, ActNo, @chars(Text,A,B)) : idiomatics(Act,_,_,Text) } 1 :- action(ID,ActNo,Act), scene(ID, A, B, _, _).   %TODO fancytext in idiomatics final position
1{ storify(ID, ActNo+1, @chars(Text,A,B)) : closing_action(Close, _, Text)} 1 :- end(ID,ActNo,Close), scene(ID, A, B, _, _).


#show start/3.
#show action/3.
#show end/3.
#show storify/3.
#show intro/2.
#show charInScene/2.


#script (python)
import gringo


def chars(text, a, b):
	return text.replace("A", a).replace("B", b)

def on_model(model):
	story = {}
	scenes = 0
	for m in model.atoms():
		if m.name() == "storify":
			scene, action, text = m.args()
			story[(scene, action)] = text
			scenes = max(scenes, scene)
		else:
			pass
			#print m
	for s in range(1,scenes+1):
		print story[(s,0)] + ". " + story[(s,1)] + ". " + story[(s,2)] + ". " + story[(s,3)] + " - " + story[(s,4)] + ".\n"
    
    
 
def main(prg):
    prg.ground([("base", [])])
    prg.solve(None, on_model)
 
#end.