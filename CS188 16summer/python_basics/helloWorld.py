# Comment: This is a fairly simple Python script
import copy
import sys 

pia = {1,3,5}
print(sys.getrefcount(pia))

x = {}
x['oh'] = {1,2,3}
print(sys.getrefcount(x['oh']))
print('y = x:')
y = x
print('ref of "oh" in y:', sys.getrefcount(y['oh']))
#
print('z = y.copy():')
z = copy.copy(y)
print('ref of "oh" in z:', sys.getrefcount(z['oh']))
print('--------deepcopy----------')
a = copy.deepcopy(x)
print('ref of "oh" in a:', sys.getrefcount(a['oh']))
b = copy.copy(a)
c = copy.copy(a)
print('b = a.copy() && c = a.copy():')
print('ref of "oh" in a:', sys.getrefcount(a['oh']))
