# Kotlin-contextual-bandit

In many real world problems, it's not trivial go for one solution out of many possible solutions based
on a certain situation.
This problem is described as [Multi-arm bandit problem](https://en.wikipedia.org/wiki/Multi-armed_bandit). 

*Disclaimer: I am not a machine learning expert. I built neuronal network and other algorithms for fun, 
used them for natural language processing and image processing, and I really dislike the somehow 
hostile terminology that acts as an artificial boundary to machine learning for beginners. I will simplify
everything as much as possible for clarity to enable this craftsmanship to everybody.*

## Example

Given you want to show a preview of an article on top of a website. What article do you show if there 
are many options? Additionally, new articles are created all the time.

An ideal solution requires some advanced approaches:

- Pick 1 out of many articles,
- A visitors taste is different, this includes comparing one with another, but maybe also the taste of a single person in the morning and in the evening,
- New articles can outperform a previous best article.

## Existing solutions

There are many different approaches that promise to solve the problem. Usually, tools should help to get things done. 
In my very subjective opinion, existing solutions are very hard to use right now.

### Vowpal Wabbit

Url: https://vowpalwabbit.org/

Vowpal Wabbit is a well known library. Teams use it in production successfully. It also contains an implementation for
contextual bandits. Nevertheless, I think it's very hard to use for beginner. Missing explanation and unexpected behavior is a core problem.

### AWS Sagemaker RL

Url: https://aws.amazon.com/de/blogs/aws/amazon-sagemaker-rl-managed-reinforcement-learning-with-amazon-sagemaker/

Amazon provides example for reinforcement problems, that spans toward contextual bandits. Nevertheless, it's not well 
maintained example that does not work anymore. It does not focus on the actual problem that, again, is 
essential to build an end-to-end pipeline. The image that does the work uses vowpal wabbit too. In my opinion, the example
could be stripped down to a few hundred lines of code. Well, that would be 2 easy, right ? Unfortunately, they decided to make it
a huge blackbox that will cause you troubles in production.

## Solution

### Motivation

My solution to the problem is keep it simple! Build it from scratch, understand challenges, add complexity when needed, and eventually use a library. 
Maybe you are happy with an existing library, or you want to build on this implementation. There is production ready yet, so let's see what we get 😉.

### Implementation

I will build during multiple sessions a bandit algorithm, documented and documented from different angles,
explaining and solving the core problems. There is no math PhD required. Only rationality and software engineering skills.

All sessions will be streamed on twitch. So you can not only participate via GitHub, you can actually participate in realtime.

Additionally, sessions will be covered by articles:
- TBD

### Constraints

In comparison to image processing or natural language processing, the performance constrains of a JVM
based solution are totally fine for a high traffic production usage. The example in this repository
will be written in Kotlin. Anyhow, the aggregation of thousands of events per second should but
implemented in a horizontal scalable solution, for example based any ETL solution like [GCP Dataflow](https://cloud.google.com/dataflow) or
[AWS Data Pipeline](https://aws.amazon.com/de/datapipeline/). The output will contain calculation results
and can be easily processed in Java to train and update the model.
