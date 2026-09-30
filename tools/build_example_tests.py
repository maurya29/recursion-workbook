"""Create an independent input fixture for each optimized/recursive method."""
import json
import re
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
decoder=json.JSONDecoder()
unordered={'Generate Parentheses','Subsets','Subsets II','Permutations','Permutations II',
           'Letter Combinations of a Phone Number','Combinations','Combination Sum','Combination Sum II',
           'Combination Sum III','N-Queens','Palindrome Partitioning','Restore IP Addresses','Rat in a Maze'}
cases=[]
for p in json.loads((ROOT/'workbook-index.json').read_text()):
    ex=p['examples'][0]
    fields={};remaining=ex['input']
    while remaining.strip():
        match=re.match(r'\s*,?\s*(\w+)\s*=\s*',remaining)
        assert match,(p['title'],remaining)
        value,end=decoder.raw_decode(remaining[match.end():])
        fields[match.group(1)]=value
        remaining=remaining[match.end()+end:]
    for method in p['methods']:
        names=[v.strip().rsplit(' ',1)[-1] for v in method['parameters'].split(',')]
        mode='equal'
        if p['title'] in unordered: mode='unordered'
        elif p['title']=='Reorganize String': mode='reorganize'
        elif p['title']=='Longest Palindromic Substring': mode='palindrome'
        elif method['returnType']=='void':mode='mutated'
        cases.append(dict(id=p['topic']+'-'+str(p['number'])+'-'+method['name'],className=p['className'],
                          method=method['name'],args=[fields[name] for name in names],mode=mode,expected=json.loads(ex['output'])))
assert len(cases)==200
out=ROOT/'src/test/resources';out.mkdir(parents=True,exist_ok=True)
(out/'examples.json').write_text(json.dumps(cases,indent=2),encoding='utf-8')
print('Prepared 200 example tests covering both variants of every question.')
