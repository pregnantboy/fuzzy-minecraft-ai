//
//  RuleInput.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "RuleInput.h"

@implementation RuleInput
- (instancetype) init {
    self = [super init];
    if (self) {
        self.cond = [[RuleCondition alloc] init];
    }
    return self;
}

- (instancetype) initWithOperator:(NSString *)op cond:(NSString *)condCode equality:(NSString *)isnot value:(NSString *)val {
    self = [super init];
    if (self) {
        self.operator = op;
        self.cond = [[RuleCondition alloc] initWithCode:condCode value:val];
        self.equality = isnot;
    }
    return self;
}

- (void)setCondition:(NSString *)condCode {
    
}


@end
