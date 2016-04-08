import tsv
import re

def string_list(s):
    return [x.strip() for x in s.split(',')]


relations = tsv.read_tsv('relationships.tsv')
# midpoints = tsv.read_tsv('midpoints.tsv')


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



