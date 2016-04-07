% vocabulary for describing cards
suit(spades; clubs; hearts; diamonds).
rank(2..10).
rank(j; q; k; a).
card(c(R,S)) :- rank(R); suit(S).

% a dealing of cards onto a grid
#const g = 5.
dim(1..g).
1 { card_at(X,Y,C): card(C) } 1 :- dim(X); dim(Y). % every grid point has a
% single card
:- card(C); 2 { card_at(X,Y,C) }. % bad if a card is used more than once
