# Instantiatable
1) Create
2) Delete

# Named
1) Rename
2) Find Usages

# Valued
1) Introduce 
   1) Variable
      1) Functional
   2) Field
      1) Constant
   3) Parameter
      1) Functional
      2) Delegate Through Overload
   4) Method
2) Replace Duplicates
   1) Scoped
   2) Locally
   3) In Class
   4) In Package
   5) In Module
3) Inline

# Refactorings
1) Package - Instantiatable, Named
   1) Compose
   2) Decompose
2) File - Instantiatable
   1) Create
      1) Without Package
      2) With Package
   2) Move To
3) Import
   1) Find Usages
   2) Remove Duplicates
   3) Sort
   4) Optimize
4) Class - Instantiatable, Named
    1) Change visibility
5) Annotation - C/D
6) Definition - Named
   1) Annotations - Instantiatable, Named
   2) Modifier
      1) Make final
   3) Type - Named
      1) Replace with implicit
      2) Replace with explicit
7) Assignment - Named, Valued
8) Declaration - Definition
   1) Merge with assignment
9) Initialization - Definition, Valued
   1) Split into declaration and assignment
10) Function - Instantiatable, Initialization
    1) Parameters - Instantiatable
       1) Parameter - Declaration
          1) Replace Array with Arbitrary Count
11) Invocation
    1) Static
       1) Replace with static import
       2) Replace with qualification
    2) Arguments - Instantiatable
       1) Argument - Valued
12) Try With Resources - Try
    1) Wrap with Try With Resources
    2) Replace with Direct Invocation