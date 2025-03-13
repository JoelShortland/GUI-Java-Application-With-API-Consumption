Input API: Marvel

Output API: Twilio

Claimed Tier: Hurdle (M2R, mark updated on Ed in private post)

Credit Optional Feature 1: -

Credit Optional Feature 2: -

Distinction Optional Feature: -

High Distinction Optional Feature: -

Milestone 1 Submission:
SHA: 6bbc765b9e2792dc720e63206725a5d3c8188fbb
URI: https://github.sydney.edu.au/jsho7613/SCD2_2022/tree/6bbc765b9e2792dc720e63206725a5d3c8188fbb

Milestone 1 Re-Submission:
SHA: d05772850dd5f94324b941a67006f95644443748
URI: https://github.sydney.edu.au/jsho7613/SCD2_2022/tree/d05772850dd5f94324b941a67006f95644443748

Milestone 2 Submission:
SHA: aa1b17451300bbd59c8419e173429efb75352c0e
URI: https://github.sydney.edu.au/jsho7613/SCD2_2022/tree/aa1b17451300bbd59c8419e173429efb75352c0e

Milestone 2 Re-Submission:
SHA: 3748192acd698ac0452f136aaa013e842a6dd502
URI: https://github.sydney.edu.au/jsho7613/SCD2_2022/commit/3748192acd698ac0452f136aaa013e842a6dd502

Exam Base Commit (Building off M2R):
SHA: 3748192acd698ac0452f136aaa013e842a6dd502
URI: https://github.sydney.edu.au/jsho7613/SCD2_2022/commit/3748192acd698ac0452f136aaa013e842a6dd502

Exam Submission Commit:
Latest commit on default branch (The lastminutefix branch, has been set to default)

Notes:
exam notes:
- My initial mark for M2R got tweaked on Ed

- As part of this tweak, please test on windows, as things in marking were
missed that result in linix-only bugs being present

- I allow the user to overwrite a pinned comic (if comic1 is pinned, and the user tries to pin comic2, I allow it), this
  is as the spec is rather vague on if this is permitted or not, so I'm going with the assumption that pinning a comic
  automatically unpins any existing pinned comic.

existing notes:
- Caching notice: When using the program, the only time program calls are made are with the
search feature and when going comic -> character, and since I don't need to store search results,
the only instance you will be asked to use cached data is when selecting a previously cached character
from a comic. Characters are saved during this and the search feature, so sometimes searches may take a little while, to
fix this, either wait it out or try searching again after 5 or so seconds.

- While there aren't many tests, all the logic is all tested, as only pure 'model' code is 
history, reading JSON objects (marvel object stuff), a few main model calls and the cache.
I also didn't bother to test trivial functions.

Reddit post info:
- Username and Password can be independently set in the program
- Requires 2 API keys:
- First create an app at https://ssl.reddit.com/prefs/apps/ then set environment variables:
- REDDIT_SECRET: the secret key of the reddit app
- REDDIT_CLIENT_ID: the client ID of the reddit app