import tsv
import re
from slugify import slugify


multi_tropelist = [
    ["Conflict", ["Hero", "Villain"], ["fight", "conflict", "are_defeated_by", "underestimate", "fight_against", "are_arrested_by", "are_investigated_by", "are_wanted_by", "defeat", "hit", "are_threatened_by"]],
    ["Climax", ["Hero", "Villain"], ["fight", "confront", "are_defeated_by", "are_arrested_by", "defeat"]],
    ["Demonic Possession", ["Demon", "Priest"], ["exorcized", "are_fought_by", "fight", "cast_out", "are_banished_by"]],
    ["Fugitive Arc", ["Fugitive", "Hunter"], ["are_hunted_by", "hunt", "stalk", "chase", "chase_after", "capture", "track_down", "catch", "lay_a_trap_for", "search_for"]],
    ["Blackmail", ["Blackmailer", "Blackmailee"], ["blackmail"]],
    ["The Obi-Wan", ["Mentor", "Student"], ["mentor", "guide", "advise", "are_inspired_by", "lose_faith_in", "lose_control_over", "are_disappointed_by", "are_rebuked_by", "fight_against", "counsel", "educate", "are_trained_by", "study_under", "look_up_to", "are_mentored_by"]],
    ["Professional Butt-Kisser", ["Butt-kisser", "Butt-kissee"], ["kiss_up_to"]],
    ["MacGuffin", ["Hero", "MacGuffin"], ["search_for"]],
    ["Et Tu, Brute?", ["Traitor", "Fool"], ["turn_against", "betray", "monitor", "spread_lies_about", "undermine", "take_advantage_of", "trick", "fool", "mock"]],
    # ["Love Triangle", [["Lover A", "Lover C"], ["Lover A", "Lover B"]], ["cheat_with", "cheat_on"]],
    ["Film Noir", ["Suspect", "Detective"], ["investigated", "interview", "apprehend", "pursue", "chase", "grow_suspicious_of", "interrogate", "listen_to", "speak_to"]],
    ["Evil Empire", ["Empire Leader", "Rebel"], ["suppress", "repress", "are_deposed_by", "are_toppled_by", "imprison", "fight_against"]],
    # ["Hero's Journey", ["Hero"] ["search_for", "travel", "journey"]],
    ["Romance Arc", ["Lady", "Gentleman"], ["seduce", "cheat_on", "encourage", "woo", "love", "date", "break_up_with", "divorce", "marry", "are_loved_by", "are_wooed_by", "are_pursued_by", "attract"]],
    ["Sealed Evil In A Can", ["Hero", "Monster"], ["fight", "confront", "turn_against", "are_exploited_by", "are_killed_by", "are_manipulated_by", "kill_for"]],
    # ["Damsel in Distress", ["Hero", "Damsel"], ["rescue"]]
]

tropelist = tsv.read_tsv('tropes.tsv')
noc = tsv.read_tsv('noc.tsv')
relations = tsv.read_tsv('relationships.tsv')

def string_list(s):
    return [x.strip() for x in s.split(',')]

def get_trope_actions(tropename, roles, wordlist):
    matches = []
    chars_a = []
    chars_b = []
    for item in relations:
        # reg = re.compile(r'\b(?:%s)\b' % '|'.join(wordlist))
        reg = re.compile('|'.join(wordlist))
        # ms = all (x in item[2] for x in wordlist)
        # ms = all([re.search(w, item[2]) for w in wordlist])
        ms = reg.search(item[2])
        if ms:
            matches.append(item[2])
            chars_a.append(item[1])
            chars_b.append(item[3])
    return {'trope': tropename, 'roles': roles ,'char_a': list(set(chars_a)), 'char_b': list(set(chars_b))}

def lists_overlap(a, b):
    return bool(set(a) & set(b))

def trope_label(id):
    for item in tropelist:
        if item[0] == id:
            return item[1]


def get_chars():
    chars = []
    tropeactions = []
    for item in multi_tropelist:
        tropeactions.append(get_trope_actions(item[0], item[1], item[2]))
    for item in noc[1:]:
        arches = []
        for thing in tropeactions:
            rolelist = string_list(item[-2])
            if lists_overlap(thing['char_a'], rolelist):
                arches.append(thing['roles'][0])
            if lists_overlap(thing['char_b'], rolelist):
                arches.append(thing['roles'][1])
        if not item[-1] == '':
            for wotsit in string_list(item[-1]):
                if not (wotsit == ""):
                    arches.append(trope_label(wotsit))
        chars.append({'id': slugify(item[0]), 'label': item[0], 'archetypes': list(set(arches))})
    return chars


def get_tropes():
    tropes = []
    for item in tropelist[1:]:
        arches = []
        if not item[0] == '':
            if (item[2] == 'Heroes') or (item[2] == 'Archetypes') or (item[2] == 'Villains'):
                for thing in noc[1:]:
                    if thing[-1] == item[0]:
                        arches.append(item[1])
        if len(arches) > 0:
            tropes.append({'id': item[0], 'label': item[1], 'archetypes': list(set(arches))})

    for item in multi_tropelist:
        # arches = []
        # for a in item[1]:
        #     arches.append(slugify(a))
        tropes.append({'id': slugify(item[0]), 'label': item[0], 'archetypes': item[1]})
    return tropes

def get_archetypes():
    archetypes = []
    for item in tropelist[1:]:
        if not item[0] == '':
            if (item[2] == 'Heroes') or (item[2] == 'Archetypes') or (item[2] == 'Villains'):
                archetypes.append({'id': item[0], 'label': item[1]})
    marches = []
    for item in multi_tropelist:
        marches.append(item[1])
    for items in marches:
        for item in items:
            archetypes.append({'id': slugify(item), 'label': item})
    return archetypes


def lookup_trope(symbol):
    for item in tropelist:
        if symbol == item[0]:
            return item

def trope_name(symbol):
    return lookup_trope(symbol)[1]


