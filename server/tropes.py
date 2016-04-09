import tsv
import re
from slugify import slugify


multi_tropelist = [
    ["Conflict", ["Hero", "Villain"], ["fight", "conflict"]],
    ["Climax", ["Hero", "Villain"], ["fight", "confront"]],
    ["Demonic Possession", ["Demon", "Priest"], ["exorcized"]],
    ["Fugitive Arc", ["Fugitive", "Hunter"], ["are_hunted_by"]],
    ["Blackmail", ["Blackmailer", "Blackmailee"], ["blackmail"]],
    ["The Obi-Wan", ["Mentor", "Mentee"], ["mentor"]],
    ["Professional Butt-Kisser", ["Butt-kisser", "Butt-kissee"], ["kiss_up_to"]],
    ["MacGuffin", ["Hero", "MacGuffin"], ["search_for"]],
    ["Et Tu, Brute?", ["Betrayer", "Betrayee"], ["turn_against", "betray"]],
    ["Film Noir", ["Suspect", "Detective"], ["investigated"]],
    ["Evil Empire", ["Empire Leader", "Rebel"], ["suppress", "repress"]],
    ["Romance Arc", ["Seducee", "Seducer"], ["seduce", "woo", "love"]],
    ["Sealed Evil In A Can", ["Hero", "Monster"], ["are_exploited_by"]],
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


