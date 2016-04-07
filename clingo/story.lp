#include "data.lp". 

1 { start(Init) : initial_action(Init,_,_,_)} 1.
1 { end(Close)  : closing_action(Close,_,_)} 1.

1 { mid(Act) : midpoints(Init,Act,Close,_,_) } 1.



1{ storify(ini, Text) : initial_action(Init, _, _, Text) } 1 :- start(Init).
1{ storify(mid, Text) : idiomatics(Act,_,Text,_) } 1 :- mid(Act).
1{ storify(end, Text) : closing_action(Close, _, Text)} 1 :- end(Close).


#show start/1.
#show mid/1.
#show end/1.
#show storify/2.


%% #script (python)
%% import gringo
 
%% def main(prg):
%%     prg.add("p", ["t"], "q(t).")
%%     prg.ground([("p", [2])])
%%     prg.solve()
 
%% #end.