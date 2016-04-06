import csv

def read_tsv(fname):
    graph = []
    with open(fname) as tsv:
        for line in csv.reader(tsv, dialect="excel-tab"): #You can also use delimiter="\t" rather than giving a dialect.
            graph.append(line)
