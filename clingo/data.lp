#include "verblist.lp". % verb(are_trusted_by).  /1
#include "actionpairs.lp". % actionPair(abandon,"and",are_pursued_by). /3
#include "midpoints.lp". % midpoints(help,are_exploited_by,abandon; turn_against; take_aim_at,caregiver,baby). /5
#include "initial.lp". % initial_action(are_attracted_by,6,534,"B had many attractive qualities"). /4
#include "closing.lp". % closing_action(disqualify,834,"thereafter B complained to the organizers but to no avail: the judge's decision was final"). /3
#include "idiomatics.lp". % idiomatics(curry_favor_with,16,"A curried favor with B","A assiduously curried favor with B"). /4
#include "relations.lp". % relations(advisor,(are_fired_by; are_paid_by; advise; promote; work_for; are_hired_by),political_leader). /3
#include "roles.lp". % role("Daniel Day-Lewis",actor). /2