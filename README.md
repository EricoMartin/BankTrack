# BankTrack NFC App

BankTrack provides a secure and convenient way for users to store and share their bank account details using NFC technology. Users can write their bank details onto an NFC tag and retrieve them when needed for making payments.

## Contributing
- Fork the project.
- Clone your own forked repository, run `git clone "https://github.com/[your_github_username]/BankTrack.git"`

Add remote upstream using the command `git remote add upstream "https://github.com/DevEdozie/BankTrack.git"`

- run: `git fetch upstream` - You must fetch from develop before or after checkout<br/>
- run: `git merge upstream/develop` - Merge updates from upstream<br/>
- `git checkout -b feat/user-login` - You are in the feat/user-login branch now<br/>
To push to github;<br/>
- `git add .`<br/>
- `git commit -m "feat: implemented user login"`<br/>
- `git push origin feat/user-login` - note how it ends with a branch. <br/>

### Commit Message Format: `chore`, `feature`, `bug`
For a feature: `git commit -m "feat: implemented user log-in"`<br/>
For a bug: `git commit -m "bug: fixed inconsistency in log in screen"`<br/>
For a chore: `git commit -m "chore: updated read me to include API endpoints"`<br/>

- Create your PR to the `develop` branch of this repository.
### When making a PR, your PR is expected to have the following comments:<br/>
- What is the task completed ?<br/>
- What the PR actually does  ?<br/>
- How can this PR be manually tested ?<br/>
- Any background contexts ? (maybe something a tester might not notice and be useful for testing)
- Screenshots (of your implementation - a web page, a mobile app screen or an API payload
