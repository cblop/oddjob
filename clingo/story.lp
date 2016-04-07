#include "data.lp". 

1 { start(Init) : initial_action(Init,_,_,_)} 1.
1 { end(Close)  : closing_action(Close,_,_)} 1.

1 { mid(Act) : midpoints(Init,Act,Close,_,_) } 1.

%% action_pair(A1,A2) :- 
%% 	action(A1, A, B);
%% 	action(A2, A, B).

%action_at(t, action(_,_,_)) :- action


%% #program step(t)

#show start/1.
#show end/1.
#show mid/1.


%% #script (python)
%% import gringo
 
%% def main(prg):
%%     prg.add("p", ["t"], "q(t).")
%%     prg.ground([("p", [2])])
%%     prg.solve()
 
%% #end.