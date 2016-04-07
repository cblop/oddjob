#include "roles.lp".
#include "rolenames.lp".
#include "gender.lp".
#include "hierarchy.lp".


error(ID) :- gender(ID,_), not role(ID, _).
error(ID) :- role(ID,_), not gender(ID, _).
error(Role) :- role(_,Role), not roleName(Role, _).
error(Role) :- super(_,Role), not roleName(Role, _).
error(Role) :- super(Role,_), not roleName(Role, _).

#show error/1.