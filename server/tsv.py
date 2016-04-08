import csv

def read_tsv(fname):
    rows = []
    with open(fname, 'r') as f:
        reader = csv.reader(f, dialect='excel-tab', delimiter='\t')
        for row in reader:
            rows.append(row)
    return rows

