"""Generate the selected Java workbook from attributed source snapshots."""
import json
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / 'src/main/java/com/interview/recursion'
MODULES = [(10, 'recursion', 'Recursion'), (11, 'backtracking', 'Backtracking'),
           (19, 'greedy', 'Greedy'), (17, 'dp-1d', 'DynamicProgramming1D'),
           (18, 'dp-2d', 'DynamicProgramming2D')]

def load(topic):
    return json.loads((ROOT/'workbook-data'/(topic+'.json')).read_text(encoding='utf-8'))

def adapt(p, topic, n):
    if p['name'] == 'Sudoku Solver':
        board = ['53..7....','6..195...','.98....6.','8...6...3','4..8.3..1','7...2...6','.6....28.','...419..5','....8..79']
        solved = ['534678912','672195348','198342567','859761423','426853791','713924856','961537284','287419635','345286179']
        p['examples'] = [dict(input='board = '+json.dumps([list(r) for r in board]),
            output=json.dumps([list(r) for r in solved]), explanation='Fill all blanks while preserving the given digits.')]
        p['optimizedComplexity'] = 'Time O(9^E); auxiliary space O(E), where E is the number of empty cells. Iterative backtracking uses row/column/box boolean tables.'
    if topic == 'backtracking' and n == 14:
        other = load('recursion')['problems'][13]
        p['optimizedCode'] = other['optimizedCode']
        p['examples'] = other['examples']
        p['note'] = 'Uses the equivalent iterative row/backtrack solution from Recursion Q14 to avoid cloning constraint arrays per state.'
    if topic == 'backtracking' and n == 13:
        p['optimizedCode'] = load('recursion')['problems'][15]['optimizedCode']
        p['note'] = 'Uses the equivalent 64-bit visited-mask search from Recursion Q16; the stated board bounds are at most 6 by 6.'
    if topic == 'dp-2d' and n == 20:
        p['recursiveCode'] = p['recursiveCode'].replace('  private int bestSide;','').replace('public int maximalSquare(char[][] matrix) {','public int maximalSquare(char[][] matrix) {\n    int bestSide = 0;')
        p['note'] = 'The maximum is local to each call so a module instance can be reused.'
    if topic == 'recursion' and n == 17:
        p['optimizedComplexity'] = 'Time O(n); auxiliary space O(h) for the explicit DFS stack, where h is tree height.'
    if topic == 'dp-2d' and n == 16:
        p['optimizedComplexity'] = 'Time O(K * (L + m*n)); Space O(m*n), where K is string count and L is maximum string length. Each string is counted once.'
    if p['name'] == 'N-Queens':
        for variant in ['optimized','recursive']:
            p[variant+'Complexity'] = 'Time O(n * n! + R * n^2) upper bound; auxiliary space O(n), plus O(R * n^2) output, where R is the number of valid boards.'
    if p['name'] == 'Combinations':
        p['optimizedComplexity'] = 'Time O(n*S + R*k); auxiliary space O(F*k), plus O(R*k) output. S is explored partial combinations and F is maximum pending stack states; each state copies up to k values.'
    if p['name'] == 'Combination Sum':
        p['optimizedComplexity'] = 'Time O(S * (n + D)); auxiliary space O(F*D), plus output. n is candidate count, D = target/minCandidate, S is explored states and F is maximum pending states; worst-case exponential search.'
        p['recursiveComplexity'] = 'Time O(n^(D+1) + R*D) upper bound; auxiliary space O(D + log n), plus O(R*D) output. D = target/minCandidate and R is solution count; sorted pruning reduces the search.'
    if topic == 'backtracking' and n == 7:
        p['optimizedComplexity'] = 'Time O(n * 2^n); auxiliary space O(n^3) upper bound for pending copied paths, plus output. Sorted pruning skips duplicates.'
        p['recursiveComplexity'] = 'Time O(n * 2^n); auxiliary space O(n), plus output.'
    if topic == 'backtracking' and n == 11:
        p['optimizedComplexity'] = 'Time O(n * 2^n); auxiliary space O(n^3) conservative bound for the palindrome table and pending copied paths, plus output.'
    if topic == 'backtracking' and n == 16:
        p['optimizedComplexity'] = 'Time O(n^2 * 4^(n^2)); auxiliary space O(n^4), plus output. Pending DFS states copy an n-by-n visited matrix and a path.'
    if topic == 'greedy' and n in (7,8,9,10,16):
        p['optimizedComplexity'] += ' Java sorting of int[][] uses O(n) worst-case temporary reference storage.'
        p['recursiveComplexity'] += ' Java sorting of int[][] uses O(n) worst-case temporary reference storage.'
    if topic == 'greedy' and n == 14:
        p['optimizedComplexity'] = 'Time O(n log u); Space O(u), where u is distinct card count. Each card is removed through TreeMap operations.'
        p['recursiveComplexity'] = 'Time O(n log u); Space O(u + n/groupSize), including the recursion stack.'
    return p

def extract(p, variant, n):
    code = p[variant+'Code']
    body = code.split('class Solution {',1)[1].rsplit('}',1)[0].strip('\n')
    suffix = 'Q'+str(n)+variant.capitalize()
    # Qualify top-level fields and nested state types to keep questions independent.
    symbols = re.findall(r'^  private (?:static )?(?:final )?[\w<>\[\],]+\s+(\w+)\s*(?=[=;])',body,re.M)
    symbols += re.findall(r'^  private (?:static )?class (\w+)',body,re.M)
    for symbol in set(symbols):
        body = re.sub(r'\b'+symbol+r'\b',symbol+suffix,body)
    methods = list(re.finditer(r'^  (public|private)\s+(?:static\s+)?([\w<>\[\], ?]+)\s+(\w+)\(([^)]*)\)',body,re.M))
    public = []
    for m in methods:
        access,ret,name,params = m.groups()
        new = ('q'+str(n).zfill(2)+name[0].upper()+name[1:]+variant.capitalize()) if access=='public' else name+suffix
        body = re.sub(r'(?<![.\w])'+name+r'\s*\(',new+'(',body)
        if access=='public': public.append(dict(name=new, returnType=ret.strip(), parameters=params.strip()))
    assert len(public)==1,(p['name'],variant,public)
    return body,public[0]

def comment(p,n,url):
    lines=[f'Question {n}: {p["name"]}','','Question: '+p['question'],'','Constraints: '+p['constraints'],
        '', 'Optimized time/space complexity: '+p['optimizedComplexity'],
        'Recursive time/space complexity: '+p['recursiveComplexity']]
    for i,ex in enumerate(p['examples'],1):
        lines += ['',f'Example {i}:','Input: '+ex['input'],'Output: '+ex['output'],'Explanation: '+ex.get('explanation','')]
    lines += ['','Source: '+url]
    if 'note' in p: lines += ['Adaptation: '+p['note']]
    return '  /*\n'+'\n'.join('   * '+s.replace('*/','* /') for s in lines)+'\n   */\n'

def main():
    index=[]
    for number,topic,base in MODULES:
        data=load(topic)
        assert len(data['problems'])==20
        for tier,start,end in [('Basic',1,12),('Moderate',13,20)]:
            class_name=base+tier
            sections=[]
            for n in range(start,end+1):
                p=adapt(data['problems'][n-1],topic,n)
                optimized,om=extract(p,'optimized',n)
                recursive,rm=extract(p,'recursive',n)
                url='https://maurya29.github.io/DSA-Pattern-Workbook/pages/'+topic+'.html'
                sections.append(comment(p,n,url)+'  // Optimized solution\n'+optimized+'\n\n  // Recursive solution\n'+recursive)
                index.append(dict(topic=topic,topicNumber=number,number=n,title=p['name'],className=class_name,
                                  methods=[om,rm],examples=p['examples']))
            folder=JAVA/'modules';folder.mkdir(parents=True,exist_ok=True)
            (folder/(class_name+'.java')).write_text('package com.interview.recursion.modules;\n\nimport java.util.*;\nimport com.interview.recursion.model.*;\n\n'
                +f'/** Topic {number}: {data["name"]}. {tier} questions {start}-{end}. */\npublic class {class_name} {{\n\n'
                +'\n\n'.join(sections)+'\n}\n',encoding='utf-8')
    (ROOT/'workbook-index.json').write_text(json.dumps(index,indent=2),encoding='utf-8')
    lines=['# Question index','','Each question has Optimized and Recursive entry points.','']
    previous=None
    for p in index:
        if p['className']!=previous:
            previous=p['className']
            lines += ['','## '+previous,'','| # | Question | Optimized | Recursive |','|---|---|---|---|']
        lines.append('| '+str(p['number'])+' | ['+p['title']+'](src/main/java/com/interview/recursion/modules/'+p['className']+'.java) | `'+p['methods'][0]['name']+'` | `'+p['methods'][1]['name']+'` |')
    (ROOT/'CATALOG.md').write_text('\n'.join(lines)+'\n',encoding='utf-8')
    print('Generated 10 module classes, 100 questions, and 200 solution methods.')

if __name__=='__main__': main()
