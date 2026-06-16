# Practice 5-6: JUnit 6

## GitHub Actions CI/CD

Repository:

```text
git@github.com:Ermolz/java-practice05-06.git
```

Pipeline platform link:

```text
https://github.com/Ermolz/java-practice05-06/actions
```

Workflow file:

```text
.github/workflows/ci-cd.yml
```

Pipeline steps:

1. Static code analysis with Checkstyle: `./gradlew check`
2. Automated JUnit 6 tests: included in `./gradlew check`
3. Jar build: `./gradlew jar`
4. Docker image build: `docker build -t practice5-6:${{ github.sha }} .`
5. Artifact repository upload in GitHub Actions:
   - `practice5-6-jar`
   - `practice5-6-docker-image`

## Test commands

Run all tests:

```powershell
.\gradlew.bat test
```

Run only the smoke suite:

```powershell
.\gradlew.bat test --tests "*SmokeSuite"
```

Run only the regression suite:

```powershell
.\gradlew.bat test --tests "*RegressionSuite"
```

Alternative tag-based runs:

```powershell
.\gradlew.bat smokeTest
.\gradlew.bat regressionTest
```

The project uses JUnit 6.1.0 and contains a simple test, static one-parameter parameterized tests,
static multi-parameter parameterized tests, dynamic tests with `@TestFactory`, assumptions, and
suite separation.
