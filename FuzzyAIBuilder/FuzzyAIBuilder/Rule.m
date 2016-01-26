//
//  Rule.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "Rule.h"
#import "RuleInput.h"

@implementation Rule



- (instancetype) init {
    self = [super init];
    if (self) {
        self.inputs = [[NSMutableArray alloc] init];
    }
    return self;
}

- (RuleInput *) getRuleAtIndex:(NSInteger) index {
    if (index < [self.inputs count]) {
        return [self.inputs objectAtIndex:index];
    } else {
        NSLog(@"Rule Input out of range");
        return nil;
    }
}




@end
