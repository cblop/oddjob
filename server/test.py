import tsv
import tropes
import process
import csv
import json
import simplejson as simplejson

# noc = tsv.read_tsv('noc.tsv')
# tropelist = tsv.read_tsv('tropes.tsv')

# print(tropes.lookup_trope('Kt'))
# print(tropes.trope_name('Bad'))

# could add "fate" as a character to the triples
print(process.get_trope_actions("Conflict", ["fight", "conflict"]))
print(process.get_trope_actions("Climax", ["fight", "confront"]))
print(process.get_trope_actions("Demonic Possession", ["exorcized"]))
print(process.get_trope_actions("Fugutive Arc", ["are_hunted_by"]))
print(process.get_trope_actions("Blackmail", ["blackmail"]))
print(process.get_trope_actions("The Obi-Wan", ["mentor"]))
print(process.get_trope_actions("Professional Butt-Kisser", ["kiss_up_to"]))
print(process.get_trope_actions("MacGuffin", ["search_for"]))
print(process.get_trope_actions("Et Tu, Brute?", ["turn_against", "betray"]))
print(process.get_trope_actions("Love Triangle", ["sleep_with", "cheat_with", "cheat_on"]))
print(process.get_trope_actions("Film Noir", ["investigated"]))
print(process.get_trope_actions("Evil Empire", ["empire", "rebel", "exploit", "suppress", "repress"]))
print(process.get_trope_actions("Hero's Journey", ["search_for", "travel", "journey"]))
print(process.get_trope_actions("Romance Arc", ["seduce", "woo", "love"]))
print(process.get_trope_actions("Damsel in Distress", ["save", "rescue"]))

tropelist = [
    ["Conflict", ["Hero", "Villain"], ["fight", "conflict"]],
    ["Climax", ["Hero", "Villain"], ["fight", "confront"]],
    ["Demonic Possession", ["Demon", "Priest"], ["exorcized"]],
    ["Fugitive Arc", ["Fugitive", "Hunter"], ["are_hunted_by"]],
    ["Blackmail", ["Blackmailer", "Blackmailee"], ["blackmail"]],
    ["The Obi-Wan", ["Mentor", "Mentee"], ["mentor"]],
    ["Professional Butt-Kisser", ["Butt-kisser", "Butt-kissee"], ["kiss_up_to"]],
    ["MacGuffin", ["Hero", "MacGuffin"], ["search_for"]],
    ["Et Tu, Brute?", ["Betrayer", "Betrayee"], ["turn_against", "betray"]],
    # ["Love Triangle", [["Lover A", "Lover C"], ["Lover A", "Lover B"]], ["cheat_with", "cheat_on"]],
    ["Film Noir", ["Suspect", "Detective"], ["investigated"]],
    ["Evil Empire", ["Empire Leader", "Rebel"], ["suppress", "repress"]],
    # ["Hero's Journey", ["Hero"] ["search_for", "travel", "journey"]],
    ["Romance Arc", ["Seducee", "Seducer"], ["seduce", "woo", "love"]],
    # ["Damsel in Distress", ["Hero", "Damsel"], ["rescue"]]
]

def make_tropelist():
    tropes = []
    for item in tropelist:
        result = process.get_trope_actions(item[0], item[1], item[2])
        tropes.append(result)
    return {'data': tropes}

def put(data, filename):
    jsondata = simplejson.dumps(data, indent=4, skipkeys=True, sort_keys=True)
    fd = open(filename, 'w')
    fd.write(jsondata)
    fd.close()

print(make_tropelist())

dat_data = make_tropelist()
put(dat_data, "tropedata.json")


print(tropes.get_chars()[1])

relations = tsv.read_tsv('relationships.tsv')
print(relations[1])

print(noc[4][-1])
print(noc[3])

print(tropes[1])


